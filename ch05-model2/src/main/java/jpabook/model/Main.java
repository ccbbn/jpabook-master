package jpabook.model;

import jpabook.model.entity.Member;
import jpabook.model.entity.Order;
import jpabook.model.entity.OrderStatus;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

/**
 * Created by 1001218 on 15. 4. 5..
 */
public class Main {

    public static void main(String[] args) {

        //엔티티 매니저 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성

        EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

        try {

            tx.begin(); //트랜잭션 시작
            //TODO 비즈니스 로직

            Member member = new Member();
            member.setName("나");
            member.setCity("11");
            member.setStreet("22");
            member.setZipcode("33");

            em.persist(member);

            Order order = new Order();
            order.setOrderDate(new Date());
            order.setStatus(OrderStatus.ORDER);
//            order.setMemberId(member.getId());
//            member.getOrders().add(order); 불가, 멤버테이블에는 널값이 들어가짐, 주인에는 값을 넣지 않는다
            order.setMember(member);

            em.persist(order);
            tx.commit();//트랜잭션 커밋

            tx.begin();
            Member foundMember = order.getMember();

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }

}
