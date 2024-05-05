package br.com.erudio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.erudio.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
	
	/*Na query abaixo, será feito o seguinte, vou pegar todos os campos de User q eu vou nomear de u desde q u.username 
	seja igual a u.username q eu recebi como parametro */
	
	//Aqui nao usamos a anotation Modifying pq só fez consulta
	
	@Query("SELECT u FROM User u WHERE u.userName =:userName") //Está lidando c o objeto user, e não c a tabela user
	User findByUsername(@Param("userName")String userName);	
}