package com.jpa.curso.crud_jpahibernate;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.xml.namespace.QName;

import com.jpa.curso.crud_jpahibernate.modelo.Persona;
import com.jpa.curso.crud_jpahibernate.persistence.JPAUtil;

public class App {

	private static EntityManagerFactory factory = JPAUtil.getEntityFactory();

	private static EntityManager manager = null;

	private static Scanner keyInput = new Scanner(System.in);
	private static String opcion = "";

	private static Persona persona = null;
	private static String codigo = null;

	public static void main(String[] args) {

		boolean estado = true;

		while (estado) {

			getOpcionMenu();

			if (opcion.matches("[1-6]")) {

				switch (opcion) {

				case "1":
					insert();
					break;

				case "2":
					update();
					break;

				case "3":
					
					search();
					break;

				case "4":
					
					delete();
					break;
				case "5":
					list();
					break;
				case "6":

					exit();
					estado = false;
					break;
				default:
					break;
				}

			} else
				System.out.println("Opcion " + opcion + " no existe dentro del menu");

			System.out.println("\n");

		}

		keyInput.close();

	}

	private static void getOpcionMenu() {

		System.out.println("CRUD JPA+HIBERNATE");
		System.out.println("1) Agregar Persona");
		System.out.println("2) Actualizar Persona");
		System.out.println("3) Buscar Persona");
		System.out.println("4) Eliminar Persona");
		System.out.println("5) Listar Personas");
		System.out.println("6) Salir");
		System.out.print("Ingrese una opcion: ");
		opcion = keyInput.nextLine();

	}

	private static void insert() {
		manager = factory.createEntityManager();

		persona = new Persona();
		persona.setId(null);

		System.out.print("Ingrese nombre: ");
		persona.setNombre(keyInput.nextLine().toUpperCase());
		System.out.println();
		System.out.print("Ingrese edad: ");
		persona.setEdad(Integer.parseInt(keyInput.nextLine().trim()));
		manager.getTransaction().begin();
		manager.persist(persona);
		manager.getTransaction().commit();
		manager.close();

	}

	private static void update() {
		manager = factory.createEntityManager();

		System.out.print("Ingrese el codigo persona actualizar: ");
		codigo = keyInput.nextLine();

		manager.getTransaction().begin();
		persona = manager.find(Persona.class, Long.parseLong(codigo));
		if (persona != null) {

			System.out.print("Ingrese nuevo nombre: ");
			persona.setNombre(keyInput.nextLine().toUpperCase());
			System.out.println();
			System.out.print("Ingrese nueva  edad: ");
			persona.setEdad(Integer.parseInt(keyInput.nextLine().trim()));
			persona = manager.merge(persona);
			System.out.println("Persona actualizada :" + persona);
		} else
			System.out.println("La persona con id " + codigo + " no existe");

		manager.getTransaction().commit();

		manager.close();

	}

	private static void search() {

		manager = factory.createEntityManager();

		System.out.print("Ingrese el codigo persona buscar: ");
		codigo = keyInput.nextLine();

		manager.getTransaction().begin();
		persona = manager.find(Persona.class, Long.parseLong(codigo));
		if (persona != null) {

			System.out.println(persona);
		} else
			System.out.println("La persona con id " + codigo + " no existe");

		manager.getTransaction().commit();

		manager.close();

	}
	private static void delete() {
		
		manager = factory.createEntityManager();

		System.out.print("Ingrese el codigo persona eliminar: ");
		codigo = keyInput.nextLine();

		manager.getTransaction().begin();
		persona = manager.find(Persona.class, Long.parseLong(codigo));
		if (persona != null) {
			manager.remove(persona);
			System.out.println("Persona eliminada correctamente");
		} else
			System.out.println("La persona con id " + codigo + " no existe");

		manager.getTransaction().commit();

		manager.close();
	}
	private static void list() {
		manager = factory.createEntityManager();

		manager.getTransaction().begin();

		Query query = manager.createQuery("FROM Persona");
		List<Persona> personas = query.getResultList();

		personas.forEach(x -> System.out.println(x));
		manager.getTransaction().commit();

		manager.close();

	}

	private static void exit() {
		System.out.println("Hasta pronto");
	}
}
