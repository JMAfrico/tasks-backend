package br.ce.wcaquino.taskbackend.controller;

import java.time.LocalDate;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;

public class TaskControllerTest {

	@Mock
	private TaskRepo taskrepo;
	
	@InjectMocks
	TaskController controller;
	
	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao(){
		Task tarefa = new Task();
		//tarefa.setTask("Descrição");
		tarefa.setDueDate(LocalDate.now());
		try {
			controller.save(tarefa);
			Assert.fail("Não deveria chega até aqui");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the task description", e.getMessage());
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaSemData() {
		Task tarefa = new Task();
		tarefa.setTask("Descrição");
		//tarefa.setDueDate(LocalDate.now());
		try {
			controller.save(tarefa);
			Assert.fail("Não deveria chega até aqui");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the due date", e.getMessage());
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() {
		Task tarefa = new Task();
		LocalDate date = LocalDate.now();
		date = date.minusDays(1);
		
		tarefa.setTask("Descrição");
		tarefa.setDueDate(date);
		try {
			controller.save(tarefa);
			Assert.fail("Não deveria chega até aqui");
		} catch (ValidationException e) {
			Assert.assertEquals("Due date must not be in past", e.getMessage());
		}
	}
	
	@Test
	public void deveSalvarTarefaComSucesso() throws ValidationException {
		Task tarefa = new Task();
		tarefa.setTask("Descrição");
		tarefa.setDueDate(LocalDate.now());
		controller.save(tarefa);
		Mockito.verify(taskrepo).save(tarefa);
	}
}
