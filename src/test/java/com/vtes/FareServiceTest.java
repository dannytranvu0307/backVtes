package com.vtes;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import com.vtes.entity.Fare;
import com.vtes.repository.FareRepo;
import com.vtes.service.FareService;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase
@DataJpaTest
@Commit
public class FareServiceTest {
	
	@TestConfiguration
	public class TestConfig {
		 @Bean
		 FareService fareService() {
			 return new FareService();
		 }
	}
	@Autowired
	FareService fareService;
	
	@Test
	public void findFareByIdTest() {
		List<Fare> fares = fareService.findAll();
		assertThat(fares.get(1)).
	}
	

}
