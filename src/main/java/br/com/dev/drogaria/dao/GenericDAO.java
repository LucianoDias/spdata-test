package br.com.dev.drogaria.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import java.lang.reflect.ParameterizedType;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;


import br.com.dev.drogaria.util.HibernateUtil;

public class GenericDAO<Entidade> {
	
	private Class<Entidade> classe;
	
	@SuppressWarnings("unchecked")
	public GenericDAO(){
		this.classe = (Class<Entidade>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	// method salvar 
	public void salvar(Entidade  entidade){
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;
		try{
			transacao = sessao.beginTransaction();
			sessao.save(entidade);
			transacao.commit();
		}catch(RuntimeException erro){
			if(transacao !=null){
				transacao.rollback();
			}
			throw erro;
		}
		finally{
			sessao.close();
		}	
	}
	
	// method listar 
	@SuppressWarnings("unchecked")
	public List<Entidade> listar() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try{
			Criteria consulta = sessao.createCriteria(classe);
			List<Entidade> resultado = consulta.list();
			return resultado;
		}catch(RuntimeException erro){
			throw erro;	
		} finally {
			sessao.close();
		}
	}
	
	// method listar ordernado
		@SuppressWarnings("unchecked")
		public List<Entidade> listar(String campoOrdenacao) {
			Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
			try{
				Criteria consulta = sessao.createCriteria(classe);
				consulta.addOrder(Order.asc(campoOrdenacao));
				List<Entidade> resultado = consulta.list();
				return resultado;
			}catch(RuntimeException erro){
				throw erro;	
			} finally {
				sessao.close();
			}
		}
	
	// method buscar por id
	@SuppressWarnings("unchecked")
	public Entidade buscar(Long codigo){
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try{
			Criteria consulta = sessao.createCriteria(classe);
			consulta.add(Restrictions.idEq(codigo));
			Entidade resultado = (Entidade)consulta.uniqueResult();
			return resultado;
			
		}catch(RuntimeException erro){
			throw erro;	
		}finally {
			sessao.close();
		}	
	}
	
	// method delete
	public void excluir(Entidade  entidade){
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;
		try{
			transacao = sessao.beginTransaction();
			sessao.delete(entidade);
			transacao.commit();
		}catch(RuntimeException erro){
			if(transacao !=null){
				transacao.rollback();
			}
			throw erro;
		}
		finally{
			sessao.close();
		}	
	}
	
	// method editar
	public void editar(Entidade  entidade){
			Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
			Transaction transacao = null;
			try{
				transacao = sessao.beginTransaction();
				sessao.update(entidade);
				transacao.commit();
			}catch(RuntimeException erro){
				if(transacao !=null){
					transacao.rollback();
				}
				throw erro;
			}
			finally{
				sessao.close();
			}	
	}
	// method merge 
		public void merge(Entidade  entidade){
			Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
			Transaction transacao = null;
			try{
				transacao = sessao.beginTransaction();
				sessao.merge(entidade);
				transacao.commit();
			}catch(RuntimeException erro){
				if(transacao !=null){
					transacao.rollback();
				}
				throw erro;
			}
			finally{
				sessao.close();
			}	
		}
		

}
