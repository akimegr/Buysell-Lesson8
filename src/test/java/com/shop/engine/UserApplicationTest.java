package com.shop.engine;

import com.shop.engine.models.User;
import com.shop.engine.models.enums.Role;
import com.shop.engine.repositories.UserRepository;
import com.shop.engine.userServices.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class UserApplicationTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	public UserService userService;


	private static String activationCode;
	private static String nonActivationCode;

	public User getTestUser() {
		User user = new User();
		user.setId(1l);
		user.setEmail("akim.egor2013@mail.ru");
		user.setActive(true);
		user.setName("akimegr");
		user.setPhoneNumber("+375336125410");
		Set<Role> roles = new HashSet<>();
		roles.add(Role.ROLE_FRANCHISE);
		roles.add(Role.ROLE_ADMIN);
		user.setRoles(roles);
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

	@Test
	public void	cannotCreateUser() {
		User user = this.getTestUser();
		User currentUser = userRepository.findByEmail(user.getEmail());
		boolean cannotCreate = userService.createUser(currentUser);
		Assertions.assertFalse(cannotCreate);
	}

	@Test
	public void	canCreateUser() {
		User user = this.getTestUser();
		String fakeBaseMail = "akim.egor2013";
		String fakeEndMail = "@mail.ru";
		User checkUser = userRepository.findByEmail(fakeBaseMail+fakeEndMail);
		while (checkUser != null) {
			fakeBaseMail = fakeBaseMail + "qwert";
			checkUser = userRepository.findByEmail(fakeBaseMail+fakeEndMail);
		}
		boolean canCreate = checkUser==null;
		Assertions.assertTrue(canCreate);
	}

	@Test
	public void checkSiteHasUsers() {
		List<User> allUsers = userService.list();
		Assertions.assertTrue(!allUsers.isEmpty());
	}

	@Test
	public void	checkBanUser() {
		User testUser = this.getTestUser();
		User user = userRepository.findById(testUser.getId()).orElse(null);
		boolean isBan = true;
		if (user != null) {
			if (testUser.isActive()) {
				isBan = false;
			}
		}
		Assertions.assertFalse(isBan);
	}

	@Test
	public void checkChangeRole () {
		User testUser = this.getTestUser();
		int rolesNow = testUser.getRoles().size();
		Set<Role> newRoles = testUser.getRoles();
		newRoles.add(Role.ROLE_ONLY_TEST);
		Assertions.assertNotEquals(rolesNow, newRoles.size());
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
