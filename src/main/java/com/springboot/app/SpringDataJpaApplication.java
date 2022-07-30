package com.springboot.app;

import com.springboot.app.service.IUploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringDataJpaApplication implements CommandLineRunner {

    @Autowired
    private IUploadFileService uploadFileService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
      /*uploadFileService.deleteAll();
      uploadFileService.init();

      String password = "1234";

      for (int i = 0; i<2;i++){
          String bcryptPassword = passwordEncoder.encode(password);
          System.out.println(bcryptPassword);
      }*/
    }
}
