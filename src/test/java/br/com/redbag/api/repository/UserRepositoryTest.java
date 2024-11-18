package br.com.redbag.api.repository;

import br.com.redbag.api.dto.request.UserRequestDto;
import br.com.redbag.api.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")

class UserRepositoryTest {

	@Autowired
	EntityManager entityManager;
	@Autowired
	UserRepository userRepository;

	@Test
	@DisplayName("Should return true if user exists by email")
	@Transactional
	void UserExistsByEmailSuccess() {
		String email = "rebeca@gmail.com";
		UserRequestDto data = new UserRequestDto("Rebeca", "Berreca", email, "123456");
		this.createUser(data);

		Boolean result = this.userRepository.existsByEmail(email);

		assertThat(result).isTrue();
	}

	@Test
	@DisplayName("Should return false if user does not exist by email")
	void UserExistsByEmailFail() {
		String email = "rebeca@gmail.com";
		Boolean result = this.userRepository.existsByEmail(email);
		assertThat(result).isFalse();
	}

	@Test
	@DisplayName("Should return true if user exists by username")
	@Transactional
	void UserExistsByUsernameSuccess() {
		String username = "JoaoSilva";
		UserRequestDto data = new UserRequestDto("Joao", username, "joao@gmail.com", "123456");
		this.createUser(data);

		Boolean result = this.userRepository.existsByUsername(username);
		assertThat(result).isTrue();
	}

	@Test
	@DisplayName("Should return false if user does not exist by username")
	void UserExistsByUsernameFail() {
		String username = "JoaoSilva";
		Boolean result = this.userRepository.existsByUsername(username);
		assertThat(result).isFalse();
	}

	@Test
	@DisplayName("Should return a user by username")
	@Transactional
	void FindByUsernameOrEmailSuccessByUsername() {
		String email = "maria@gmail.com";
		String username = "MariaSilva";
		UserRequestDto data = new UserRequestDto("Maria", username, "joao@gmail.com", "123456");
		this.createUser(data);
		UserDetails result = this.userRepository.findByUsernameOrEmail(username, email);
		assertThat(result).isNotNull();

	}

	@Test
	@DisplayName("Should return a user by email")
	@Transactional
	void FindByUsernameOrEmailSuccessByEmail(){
		String email = "maria@gmail.com";
		String username = "MariaSilva";
		UserRequestDto data = new UserRequestDto("Maria", "JoaoSilva", email, "123456");
		this.createUser(data);
		UserDetails result = this.userRepository.findByUsernameOrEmail(username, email);
		assertThat(result).isNotNull();
	}

	@Test
	@DisplayName("Should return null if user does not exist by username or email")
	void FindByUsernameOrEmailFail() {
		String email = "joao@gmail.com";
		String username = "JoaoSilva";
		UserDetails result = this.userRepository.findByUsernameOrEmail(username, email);
		assertThat(result).isNull();
	}

	private User createUser(UserRequestDto data) {
		User newUser = new User(data);
		this.entityManager.persist(newUser);
		return newUser;
	}
}