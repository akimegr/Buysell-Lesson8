package com.shop.engine;

import com.shop.engine.models.User;
import com.shop.engine.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class UserApplicationTest {

	@Mock
	private UserRepository userRepositoryMock;

	@Autowired
	private UserRepository userRepository;


	private static String activationCode;
	private static String nonActivationCode;

	public User getTestUser() {
		User user = new User();
		user.setId(1l);
		user.setEmail("akim.egor2013@mail.ru");
		user.setActive(true);
		user.setName("akimegr");
		user.setPhoneNumber("+375336125410");
		return user;
	}



	@Test
	public void findById() {
		User user = this.getTestUser();
		Optional<User> currentUser = userRepository.findById(user.getId());
		User myUser = currentUser.orElse(null);

		Assertions.assertNotNull(myUser);
	}

	@Test
	public void findByEmail() {
		User user = this.getTestUser();
		User currentUser = userRepository.findByEmail(user.getEmail());

		Assertions.assertNotNull(currentUser);
	}


	@Test
	public void findByActive() {
		User user = this.getTestUser();
		List<User> currentUsers = userRepository.findByActive(user.isActive());

		Assertions.assertNotNull(currentUsers);
	}

	@Test
	public void findByName() {
		User user = this.getTestUser();
		User currentUsers = userRepository.findByEmail(user.getEmail());

		Assertions.assertNotNull(currentUsers);
	}

	@Test
	public void findByPhone() {
		User user = this.getTestUser();
		User currentUser = userRepository.findByPhoneNumber(user.getPhoneNumber());
		Assertions.assertNotNull(currentUser);
	}




//	@Test
//	public void findByActivationCode_nonActivationCode_null() {
//		User currentUser = userRepository.findByActivationCode(nonActivationCode);
//
//		Assert.assertNull(currentUser);
//	}
//
//	@Test
//	public void save_user_userWithId() {
//		User currentUser = userRepository.save(user);
//
//		Assert.assertNotNull(currentUser.getId());
//	}



	@Test
	void contextLoads() {
	}

}
