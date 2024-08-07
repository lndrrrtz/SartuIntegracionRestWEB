package net.edu.sarturest.core.facades;

import java.util.List;

import net.edu.sarturest.core.beans.Usuario;

public interface UsuarioFacade {
	
	/**
	 * Obtiene todos los usuarios dados de alta en Sartu
	 * 
	 * @return {@link List<Usuario>} con datos de los usuarios de Sartu
	 */
	List<Usuario> leerUsuarios();
}
