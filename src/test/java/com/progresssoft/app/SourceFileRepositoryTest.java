package com.progresssoft.app;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.progresssoft.app.dao.SourceFileRepository;
import com.progresssoft.app.model.SourceFile;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=ClusterDataApplication.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class SourceFileRepositoryTest {

	@Autowired
	private SourceFileRepository sourceFileRepository;

	@Test
	public void whenValidFileNamethenReturnCurrency() {
		// given
		SourceFile sourceFile = new SourceFile("new_deal.csv", new Date());
		sourceFileRepository.save(sourceFile);

		// when
		SourceFile found = sourceFileRepository.findByFileName(sourceFile.getFileName());

		assertThat(found.getFileName()).isEqualTo(sourceFile.getFileName());
	}

}
