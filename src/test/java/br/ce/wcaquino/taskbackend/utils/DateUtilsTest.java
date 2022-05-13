package br.ce.wcaquino.taskbackend.utils;

import java.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;

public class DateUtilsTest {

	@Test
	public void deveRetornarTrueParaDatasFuturas() {
		LocalDate date = LocalDate.of(2030, 05 ,21);
		Assert.assertTrue(DateUtils.isEqualOrFutureDate(date));
	}
	
	@Test
	public void deveRetornarTrueParaDataAmanha() {
		LocalDate date = LocalDate.now();
		date = date.plusDays(1);
		Assert.assertTrue(DateUtils.isEqualOrFutureDate(date));
		System.out.println(date);
	}
	
	@Test
	public void deveRetornarTrueParaDataAtual() {
		LocalDate date = LocalDate.now();
		Assert.assertTrue(DateUtils.isEqualOrFutureDate(date));
	}
	
	@Test
	public void deveRetornarFalseParaDataAmanha() {
		LocalDate date = LocalDate.now();
		date = date.minusDays(1);
		Assert.assertFalse(DateUtils.isEqualOrFutureDate(date));
		System.out.println(date);
	}
	
	@Test
	public void deveRetornarFalseParaDatasPassadas() {
		LocalDate date = LocalDate.of(2020, 05 ,21);
		Assert.assertFalse(DateUtils.isEqualOrFutureDate(date));
	}
}
