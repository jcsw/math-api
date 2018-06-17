package br.com.jcsw.math.domain;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions( //
    plugin = {"pretty", "json:target/"}, //
    features = "src/test/resources/features")
public class CucumberRoot {

}
