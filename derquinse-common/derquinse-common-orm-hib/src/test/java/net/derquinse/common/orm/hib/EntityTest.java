/*
 * Copyright (C) the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.derquinse.common.orm.hib;

import java.io.ByteArrayInputStream;
import java.security.SecureRandom;
import java.util.UUID;

import net.derquinse.common.base.ByteString;
import net.derquinse.common.base.Digests;
import net.derquinse.common.test.EqualityTests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.common.io.ByteStreams;
import com.google.common.io.InputSupplier;

/**
 * Tests and entity with custom user types.
 * @author Andres Rodriguez
 */
public class EntityTest {

	@Test
	public void test() throws Exception {
		byte[] bytes = new byte[1024];
		new SecureRandom().nextBytes(bytes);
		InputSupplier<ByteArrayInputStream> is = ByteStreams.newInputStreamSupplier(bytes);
		ByteString sha1 = ByteString.copyFrom(ByteStreams.getDigest(is, Digests.sha1()));
		DateTime t = DateTime.now().minusMonths(3);
		final UUID id = UUID.randomUUID();
		TestUUIDEntity e1 = new TestUUIDEntity();
		e1.setId(id);
		e1.dateTime = t;
		e1.sha1 = sha1;
		@SuppressWarnings("deprecation")
		SessionFactory sessionFactory = TestConfigurations.h2(TestConfigurations.types()).buildSessionFactory();
		Session s1 = sessionFactory.openSession();
		Transaction tx = s1.beginTransaction();
		s1.save(e1);
		tx.commit();
		s1.close();
		Session s2 = sessionFactory.openSession();
		TestUUIDEntity e2 = (TestUUIDEntity) s2.get(TestUUIDEntity.class, id);
		s2.close();
		EqualityTests.many(id, e1.getId(), e2.getId());
		EqualityTests.many(t, e1.dateTime, e2.dateTime);
		EqualityTests.many(sha1, e1.sha1, e2.sha1);
		Assert.assertNotSame(e1.dateTime, e2.dateTime);
		Assert.assertNotSame(e1.sha1, e2.sha1);
		EqualityTests.two(e1, e2);
		Assert.assertEquals(e1.hashCode(), id.hashCode());
		Assert.assertEquals(e2.hashCode(), id.hashCode());
		UUID id2 = id;
		while (id.equals(id2)) {
			id2 = UUID.randomUUID();
		}
		e2.setId(id2);
		Assert.assertTrue(!e2.equals(e1));
		Assert.assertEquals(e2.hashCode(), id2.hashCode());
	}
}
