package com.vti;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Commit;

import com.vti.entity.Account;
import com.vti.service.AccountService;


@AutoConfigureTestDatabase(replace = Replace.NONE)
@SpringBootTest
@Commit
public class AccountServiceTest {
	
	@TestConfiguration
	class TestConfig{
		@Bean
		AccountService userService() {
			return new AccountService();
		}
	}

	@Autowired
	 AccountService accountService;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	@Test
	public void TestSaveNewUser_thenReturnSavedUser() {
		Account account= new Account(1L, "chient369@gmail.com", passwordEncoder.encode("111111111"),"NGUYEN VAN A",null, false, null, null, null);
		Account savedAcc = accountService.saveAccount(account);
		
		assertThat(account.getId().equals(savedAcc.getId()));
	}
	@Test
	public void TestFindUserByEmail_thenReturnUserId1() {
		String email = "chient369@gmail.com";
		Account acc = accountService.findAccountByEmail(email);
		
		assertThat(acc.getId().equals(1));
	}
}
