package com.meli.mutant.config.imp;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.meli.mutant.config.IMutantFinderConfig;
import com.meli.mutant.exception.MutantFinderException;
import com.meli.mutant.model.Dna;
import com.meli.mutant.model.Stats;

@RestController
@EnableWebMvc
public class MutantFinderController {

	@Autowired
	IMutantFinderConfig mutanFinderConfig;

	@RequestMapping(path = "/mutant", method = RequestMethod.POST)
	public ResponseEntity<String> isMutant(@RequestBody Dna dna) {

		String[] dnaList = dna.getDna().toArray(new String[0]);

		try {
			boolean isMutant = mutanFinderConfig.mutantFinderBean().isMutant(dnaList);
			if (!isMutant)
				return new ResponseEntity<String>("Not Mutant", HttpStatus.FORBIDDEN);
		} catch (MutantFinderException mfe) {
			return new ResponseEntity<String>(mfe.getErrorMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("Ok", HttpStatus.OK);
	}

	@RequestMapping(path = "/mutant/stats", method = RequestMethod.GET)
	public ResponseEntity<Stats> mutantStats(Principal principal) {

		Stats stats = new Stats();
		stats.setCount_human_dna(100);
		stats.setCount_mutant_dna(40);
		stats.setRatio(stats.getCount_mutant_dna() / stats.getCount_human_dna());

		return new ResponseEntity<Stats>(stats, HttpStatus.OK);
	}

}
