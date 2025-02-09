package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.jdbc.core.JdbcTemplate;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.sqs.AmazonSQSAsync;

@RestController
@RequestMapping("/aws/s3")
public class AwsController {
	
	
	@Autowired
	private AmazonS3 amazonS3;
	
	@Autowired
	private AmazonSQSAsync sqs;
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//Inseriri no bucket
	@PostMapping("/inserebucket")
	public void note1(@RequestParam String name, String content) {
		amazonS3.putObject("file", name + ".txt", content );
		
		
	}
	
	
	//inserir script na tabela
	//http://localhost:8080/aws/s3/consome?name=Recado&content=Ligar
//	create database blog_pessoal;
//	use blog_pessoal;
//
//	create table nota(
//		name varchar(30),
//		conteudo varchar(100));
//		
//	select * from nota
	@PostMapping("/insere")
	public void consome(@RequestParam String name, String content) {
		jdbcTemplate.update("insert into nota (name, conteudo) values (?, ?)", name, content);
		
		
		
	}
	

	//Insere na fila
	@PostMapping("/produz")
	public void produz(@RequestParam String name, String content) {
		
		new  QueueMessagingTemplate(sqs).convertAndSend("NOTA_QUEUE", content);
		amazonS3.putObject("file", name + ".txt", content );
		
		
	}
	//Observa itens da mensageria
	@SqsListener("NOTA_QUEUE")
	public void consumir(String message) {
		System.out.println("hello, " + message);
	}
	
	

}
