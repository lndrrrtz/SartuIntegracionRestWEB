package net.edu.sarturest.core.facades.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.edu.sarturest.core.beans.Usuario;
import net.edu.sarturest.core.daos.UsuarioDao;
import net.edu.sarturest.core.facades.UsuarioFacade;

@Service
public class UsuarioFacadeImpl implements UsuarioFacade {

	@Autowired
	private UsuarioDao usuarioDao;

	@Override
	public List<Usuario> leerUsuarios() {
		return usuarioDao.leerUsuarios();
	}
	
}
