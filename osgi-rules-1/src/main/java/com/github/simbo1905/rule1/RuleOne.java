package com.github.simbo1905.rule1;

import com.github.simbo1905.clashes.MagicNumber;
import com.github.simbo1905.osgi.RulesInput;
import com.github.simbo1905.osgi.RulesOutput;
import com.github.simbo1905.osgi.RulesService;

public class RuleOne implements RulesService {

	public RulesOutput apply(RulesInput request) {
		return new RulesOutput(request.getValue() + ":" + MagicNumber.NUMBER_99.name());
	}

}
