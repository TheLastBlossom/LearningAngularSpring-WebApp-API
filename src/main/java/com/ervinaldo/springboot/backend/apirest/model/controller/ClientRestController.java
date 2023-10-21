package com.ervinaldo.springboot.backend.apirest.model.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ervinaldo.springboot.backend.apirest.models.entity.Client;
import com.ervinaldo.springboot.backend.apirest.models.entity.Region;
import com.ervinaldo.springboot.backend.apirest.models.service.IClientService;
import com.ervinaldo.springboot.backend.apirest.models.service.IUploadFileService;

import javax.validation.Valid;
                                                                                   
@RestController
@RequestMapping("/api")
public class ClientRestController {
	@Autowired
	private IClientService clientService;
	@Autowired
	private IUploadFileService iUploadFileService;
	private final Logger log = LoggerFactory.getLogger(ClientRestController.class);

	@GetMapping("/clients")
	public List<Client> index() {
		return clientService.findAll();
	}

	@GetMapping("/clients/page/{page}")
	public Page<Client> index(@PathVariable Integer page) {
		return clientService.findAll(PageRequest.of(page, 4));
	}
	
//	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/clients/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Client client = null;
		Map<String, Object> response = new HashMap<>();
		try {
			client = clientService.findById(id);
			if (client == null) {
				response.put("message",
						"The client with the id ".concat(id.toString()).concat(" doesn't exits in the database!"));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
		} catch (DataAccessException e) {
			response.put("message", "Internal Error");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Client>(client, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/clients")
	public ResponseEntity<?> create(@Valid @RequestBody Client client, BindingResult result) {
//		@Valid is an interceptor
//		Biding Result is the object where the result of the validation is storaged				
		Client newClient = null;
		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errorsList = result.getFieldErrors().stream()
					.map(err -> "The field '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("Errors", errorsList);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			newClient = clientService.save(client);
		} catch (DataAccessException e) {
			response.put("message", "Internal Error");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("message", "Client has been created sucessfully");
		response.put("client", newClient);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN  ")
	@PutMapping("/clients/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Client client, @PathVariable Long id, BindingResult result) {
		Client updatedClient = null;
		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errorsList = result.getFieldErrors().stream()
					.map(err -> "The field '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("Errors", errorsList);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			Client currentClient = clientService.findById(id);
			if (currentClient == null) {
				response.put("message", "Update not possible. The client with the id ".concat(id.toString())
						.concat(" doesn't exits in the database!"));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			currentClient.setName(client.getName());
			currentClient.setSurname(client.getSurname());
			currentClient.setEmail(client.getEmail());
			currentClient.setRegion(client.getRegion());
			updatedClient = clientService.save(currentClient);
		} catch (DataAccessException e) {
			response.put("message", "Internal Error");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("message", "Client has been updated sucessfully");
		response.put("client", updatedClient);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/clients/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			Client client = clientService.findById(id);
			String lastPhoto = client.getPhoto();
			if (lastPhoto != null) {
				iUploadFileService.delete(lastPhoto);
			}
			clientService.delete(id);
		} catch (DataAccessException e) {
			response.put("message", "Internal Error");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("message", "Client has been deleted sucessfully");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@PostMapping("/clients/upload")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id) {
		Map<String, Object> response = new HashMap<>();
		Client client = clientService.findById(id);
		if (!file.isEmpty()) {
			String fileName = null;
			try {
				fileName = iUploadFileService.copy(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			// delete last photo if exists
			String lastPhoto = client.getPhoto();
			if (lastPhoto != null) {
				iUploadFileService.delete(lastPhoto);
			}
			client.setPhoto(fileName);
			clientService.save(client);
			response.put("client", client);
			response.put("message", "The image has been upload successfully");
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@GetMapping("/uploads/img/{photoName:.+}")
	public ResponseEntity<Resource> showPhoto(@PathVariable String photoName) {
		Path pathFile = Paths.get("uploads").resolve(photoName).toAbsolutePath();
		log.info(pathFile.toString());
		Resource resource = null;
		try {
			resource = iUploadFileService.upload(photoName);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		HttpHeaders header = new HttpHeaders();
		// to force download
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("clients/regions")
	public List<Region> listRegions(){
		return clientService.findAllRegiones();
	}

}
