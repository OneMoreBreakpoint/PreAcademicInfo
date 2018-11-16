package data_layer.repositories;

import data_layer.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Repository
public class UserRepository implements IUserRepository{
    @Autowired
    private EntityManager em;

    public User findOneByUsername(String username){
        try{
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE username = :username", User.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        }catch (NoResultException ex){
            return null;
        }
    }
}
