package com.github.simbo1905.rule2;

import com.github.simbo1905.clashes.MagicNumber;
import com.github.simbo1905.osgi.RulesInput;
import com.github.simbo1905.osgi.RulesOutput;
import com.github.simbo1905.osgi.RulesService;

public class RuleTwo implements RulesService {

	public RulesOutput apply(RulesInput request) {
		return new RulesOutput(request.getRequest() + ":" + MagicNumber.NUMBER_42.name());
	}

}
