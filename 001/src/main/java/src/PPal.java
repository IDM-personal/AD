package src;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.persistence.Entity;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import model.Accesos;
import model.Cocheras;
import model.Estaciones;
import model.LineaEstacion;
import model.LineaEstacionId;
import model.Lineas;
import model.MisTrenes;
import model.Train_Fetch_Obj;
import model.Viajes;


public class PPal {
	
	
	public static void printmenu()
	{
		System.out.println("1 - Modificar nombre cochera");
		System.out.println("2 - Mostrar línea en la que prestan servicio más trenes");
		System.out.println("3 - Ampliar línea");
		System.out.println("4 - Actualizar Estaciones");
		System.out.println("5 - Mostrar informacion de linea");
	}
	
	public static void modificarcochera()
	{
		Scanner sc = new Scanner(System.in);
		Session s = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = s.beginTransaction();
		Cocheras c = null;
		System.out.println("Introduce ID de cochera a cambiar el nombre");
		try
		{
			c = s.load(Cocheras.class, sc.nextInt());
			System.out.println("Introduce nuevo nombre de la cochera");
			String name = sc.next();
			System.out.println(name);
			c.setNombre(name);
			System.out.println(c.toString());
			s.saveOrUpdate(c);
			tx.commit();
		}catch (Exception e) {
			System.out.println("No existe una cochera con ese codigo");
		}
		s.close();
	}
	
	public static void marlinea()
	{
		int max = 0;
		int i = 0;
		Session s = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = s.beginTransaction();
		Query q = s.createSQLQuery("SELECT cod_linea,count(mis_trenes.cod_linea) as `val_max` from mis_trenes"
				+ " group by mis_trenes.cod_linea"
				+ " order by `val_max` desc");
		Iterator<Object> it = q.list().iterator();
		Train_Fetch_Obj[] lineasmax = new Train_Fetch_Obj[q.list().size()];
		while(it.hasNext())
		{
			Object[] marlinea = (Object[]) it.next();
			lineasmax[i] = new Train_Fetch_Obj(Integer.parseInt(marlinea[0].toString()),Integer.parseInt(marlinea[1].toString()));
			if(lineasmax[i].getVal_max() > max)
				max = lineasmax[i].getVal_max();
			i++;
		}
		System.out.println("Linea/s en la/s que prestan servicio mas trenes : ");
		for(Train_Fetch_Obj maxl: lineasmax)
			if(maxl.getVal_max() == max)
				System.out.println("Linea "+maxl.getCod_linea()+", Numero de trenes : "+ max);
		tx.commit();
		s.close();
	}
	
	public static void AmpliarLinea()
	{
		Session s = HibernateUtil.getSessionFactory().openSession();
		Scanner sc = new Scanner(System.in);
		int nlinea = 0;
		int nestacion = 0;
		System.out.println("Introduce cod linea : ");
		Transaction tx = s.beginTransaction();
		Query q = s.createQuery("from "+model.Lineas.class.getSimpleName());
		List<Lineas> lineas = q.list();
		Integer[] lexistent = new Integer[q.list().size()];
		int i = 0;
		for(Lineas linea: lineas)
		{
			System.out.println(linea.toString());
			lexistent[i++] = linea.getCodLinea();
		}
		System.out.println("Introduce un codigo de linea, valores entre : ["+lexistent[0] + " - "+lexistent[i - 1] + "]");
		while((i = sc.nextInt()) < lexistent[0] || i > lexistent[lexistent.length - 1])
		{
			System.out.println("VALOR EN WHILE "+i);
			if(i < lexistent[0] || i > lexistent[lexistent.length - 1])
				System.out.println("Introduce un valor valido en el rango : ["+lexistent[0] + " - "+lexistent[lexistent.length - 1] + "]");
		}
		nlinea = i;
		System.out.println("NLINEA "+nlinea);
		Lineas linea = s.load(Lineas.class, nlinea);
		tx.commit();
		tx = s.beginTransaction();
		q = s.createQuery("from "+model.Estaciones.class.getSimpleName());
		List<Estaciones> Estaciones = q.list();
		Integer[] eexistent = new Integer[q.list().size()];
		i = 0;
		tx.commit();
		for(Estaciones estacion: Estaciones)
		{
			System.out.println(estacion.toString());
			eexistent[i++] = estacion.getCodEstacion();
		}
		System.out.println("Introduce un codigo de Estacion, valores entre : ["+eexistent[0] + " - "+eexistent[i - 1] + "]");
		while((i = sc.nextInt()) < eexistent[0] || i > eexistent[eexistent.length - 1])
		{
			if(i < eexistent[0] || i > eexistent[eexistent.length - 1])
				System.out.println("Introduce un valor valido en el rango : ["+eexistent[0] + " - "+eexistent[eexistent.length - 1] + "]");
		}
		nestacion = i;
		Estaciones estacion = s.load(Estaciones.class, nestacion);
		int opcion;
		System.out.println("0) Añadir al final\n1) Añadir a mitad");
		while((opcion = sc.nextInt()) < 0 || opcion > 1)
		{
			if(opcion < 0 || opcion > 1)
				System.out.println("Introduce un valor valido en el rango : [0 - 1]");
		}
		s.close();
		switch (opcion) {
		case 0:
			s = HibernateUtil.getSessionFactory().openSession();
			tx = s.beginTransaction();
			Lineas l = s.load(Lineas.class, nlinea);
			Estaciones est = s.load(Estaciones.class, nestacion);
			
			int lastorden = 0;
			for(LineaEstacion lest :l.getLineaEstacions())
			{
				System.out.println("ORDEN : "+lest.getOrden());
				if(lest.getOrden() > lastorden)
					lastorden = lest.getOrden();
			}
			LineaEstacion les = new LineaEstacion(new LineaEstacionId(l.getCodLinea(), est.getCodEstacion()),estacion,l,lastorden + 1);
			System.out.println(les.toString());
			try {
				s.save(les);
			} catch (Exception e) {
				System.out.println("Error, ya existe esa estacion en esa linea");
			}
			s.flush();
			s.close();
			break;
		case 1:
			s = HibernateUtil.getSessionFactory().openSession();
			
			l = s.load(Lineas.class, nlinea);
			est = s.load(Estaciones.class, nestacion);
			System.out.println("Introduce orden de estacion");
			int neworden = sc.nextInt();
			q = s.createQuery("from "+LineaEstacion.class.getSimpleName()+" where cod_linea = :cod_linea and orden >= :orden order by orden desc")
					.setParameter("cod_linea", nlinea)
					.setParameter("orden", neworden);
			List<LineaEstacion> EstActualizar = q.getResultList();
			for(LineaEstacion Act:EstActualizar)
			{
				tx = s.beginTransaction();
				System.out.println("MI LINEA ESTACION FIRST : "+Act.toString());
				Act.setOrden(Act.getOrden() + 1);
				s.saveOrUpdate(Act);
				tx.commit();
			}
			tx = s.beginTransaction();
			LineaEstacion newLE = new LineaEstacion(new LineaEstacionId(l.getCodLinea(), est.getCodEstacion()),estacion,l,neworden);
			s.saveOrUpdate(newLE);
			tx.commit();
			s.close();
			break;
		default:
			
			break;
		}
	}
	
	public static void actEstaciones()
	{
		Scanner sc = new Scanner(System.in);
		Session s = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		Query q = s.createQuery("from "+Estaciones.class.getSimpleName());
		List<Estaciones> estaciones = q.list();
		for(Estaciones estacion: estaciones)
		{
			tx  = s.beginTransaction();
			System.out.println("Estacion al principio : "+estacion.toString());
			q = s.createQuery("select  count(cod_acceso) from "+Accesos.class.getSimpleName()+" where cod_estacion = :cod_estacion")
					.setParameter("cod_estacion", estacion.getCodEstacion());
			estacion.setNumaccesos(Integer.parseInt(q.uniqueResult().toString()));
			q = s.createQuery("select  count(dk) from "+LineaEstacion.class.getSimpleName()+" as dk where cod_estacion = :cod_estacion")
					.setParameter("cod_estacion", estacion.getCodEstacion());
			estacion.setNumlineas(Integer.parseInt(q.uniqueResult().toString()));
			q = s.createQuery("select  count(dk) from "+Viajes.class.getSimpleName()+" as dk where estacion_og = :cod_estacion")
					.setParameter("cod_estacion", estacion.getCodEstacion());
			estacion.setNumviajesproc(Integer.parseInt(q.uniqueResult().toString()));
			q = s.createQuery("select  count(dk) from "+Viajes.class.getSimpleName()+" as dk where estacion_dst = :cod_estacion")
					.setParameter("cod_estacion", estacion.getCodEstacion());
			estacion.setNumviajesdest(Integer.parseInt(q.uniqueResult().toString()));
			s.saveOrUpdate(estacion);
			tx.commit();
		}
	}
	
	public static void MostrarEstaciones() {
		Scanner sc = new Scanner(System.in);
		String nombrelinea = sc.nextLine();
		Session s = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = s.beginTransaction();
		Lineas linea = null;
		try
		{
			linea = (Lineas) s.createQuery(" from Lineas where nombre = :name order by cod_linea")
			.setParameter("name", nombrelinea) 
			.getSingleResult();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		Set<LineaEstacion> lests =  linea.getLineaEstacions();
		for(LineaEstacion lest: lests)
		{
			Estaciones est = s.get(Estaciones.class, lest.getId().getCodEstacion());
			System.out.println(est.toString());
			
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int i = 0;
		printmenu();
		while((i = sc.nextInt()) != 6)
		{
			switch (i) {
			case 1:
				modificarcochera();
				printmenu();
				break;
			case 2:
				marlinea();
				printmenu();
				break;
			case 3:
				AmpliarLinea();
				printmenu();
				break;
			case 4:
				actEstaciones();
				printmenu();
				break ;
			case 5:
				MostrarEstaciones();
				printmenu();
				break;
			default:
				printmenu();
				break;
			}
		}
		System.out.println("Fin del Programa");
	}

}
