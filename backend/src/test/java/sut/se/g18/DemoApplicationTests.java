package sut.se.g18;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.Collections;
import java.util.OptionalInt;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import sut.se.g18.Entity.*;
import sut.se.g18.Repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DemoApplicationTests {
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private BankRepository bankRepository;

	@Autowired
    private TestEntityManager entityManager;

	private Validator validator;
	
	@Before
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
	@Test
	public void testPass(){
		PaymentEntity pay = new PaymentEntity();
		pay.setName("Ball");
		pay.setAccountNumber("1112");
		pay.setAddress("sut");
		pay.setPhonenum("0801784600");
		try {
            entityManager.persist(pay);
            entityManager.flush();

            //fail("Should not pass to this line");
        } catch (javax.validation.ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            assertEquals(violations.isEmpty(), false);
            assertEquals(violations.size(), 0);
        } 
	}
	@Test
	public void testNameCannotBeNull(){
		PaymentEntity pay1 = new PaymentEntity();
		pay1.setName(null);
		pay1.setAccountNumber("1112");
		pay1.setAddress("sut");
		pay1.setPhonenum("0801784600");
		try {
            entityManager.persist(pay1);
            entityManager.flush();

            fail("Should not pass to this line");
        } catch(javax.validation.ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            assertEquals(violations.isEmpty(), false);
            assertEquals(violations.size(), 1);
        }
	}
	@Test
	public void testPhonenumLenghtLessthanNine(){
		PaymentEntity pay2 = new PaymentEntity();
		pay2.setName("Ball");
		pay2.setAccountNumber("1112");
		pay2.setAddress("sut");
		pay2.setPhonenum("08017846");
		try {
            entityManager.persist(pay2);
            entityManager.flush();

            fail("Should not pass to this line");
        } catch(javax.validation.ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            assertEquals(violations.isEmpty(), false);
            assertEquals(violations.size(), 1);
        }
	}
	@Test
	public void testBankNameStartwith(){
		BankEntity bank = new BankEntity();
		bank.setBankname("กรุงศรีอยุธยา");
		try {
            entityManager.persist(bank);
            entityManager.flush();

            fail("Should not pass to this line");
        } catch(javax.validation.ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            assertEquals(violations.isEmpty(), false);
            assertEquals(violations.size(), 1);
        }
	}
}
