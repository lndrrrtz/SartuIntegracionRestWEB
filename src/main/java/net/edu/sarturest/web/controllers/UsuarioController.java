package net.edu.sarturest.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.edu.sarturest.core.beans.Usuario;
import net.edu.sarturest.core.facades.UsuarioFacade;

@RequestMapping("/api/v1")
@RestController
public class UsuarioController {
	
	@Autowired
	private UsuarioFacade usuarioFacade;

	@RequestMapping(value = "/usuarios", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Usuario> leerUsuarios() {
		return usuarioFacade.leerUsuarios();
	}
	
	@RequestMapping(value = "/usuarios2", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Usuario> leerUsuarios2() {
		return usuarioFacade.leerUsuarios();
	}
}
