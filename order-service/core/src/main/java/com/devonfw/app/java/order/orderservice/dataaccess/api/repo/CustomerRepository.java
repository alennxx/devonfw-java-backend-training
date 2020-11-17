package com.devonfw.app.java.order.orderservice.dataaccess.api.repo;

import static com.querydsl.core.alias.Alias.$;

import java.util.Iterator;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.devonfw.app.java.order.orderservice.dataaccess.api.CustomerEntity;
import com.devonfw.app.java.order.orderservice.logic.api.to.CustomerSearchCriteriaTo;
import com.devonfw.module.jpa.dataaccess.api.QueryUtil;
import com.devonfw.module.jpa.dataaccess.api.data.DefaultRepository;
import com.querydsl.jpa.impl.JPAQuery;

/**
 * {@link DefaultRepository} for {@link CustomerEntity}
 */
public interface CustomerRepository extends DefaultRepository<CustomerEntity> {

  /**
   * @param criteria the {@link CustomerSearchCriteriaTo} with the criteria to search.
   * @return the {@link Page} of the {@link CustomerEntity} objects that matched the search. If no pageable is set, it
   *         will return a unique page with all the objects that matched the search.
   */
  default Page<CustomerEntity> findByCriteria(CustomerSearchCriteriaTo criteria) {

    CustomerEntity alias = newDslAlias();
    JPAQuery<CustomerEntity> query = newDslQuery(alias);

    String firstName = criteria.getFirstName();
    if (firstName != null && !firstName.isEmpty()) {
      QueryUtil.get().whereString(query, $(alias.getFirstName()), firstName, criteria.getFirstNameOption());
    }
    String lastName = criteria.getLastName();
    if (lastName != null && !lastName.isEmpty()) {
      QueryUtil.get().whereString(query, $(alias.getLastName()), lastName, criteria.getLastNameOption());
    }
    if (criteria.getPageable() == null) {
      criteria.setPageable(PageRequest.of(0, Integer.MAX_VALUE));
    } else {
      addOrderBy(query, alias, criteria.getPageable().getSort());
    }

    return QueryUtil.get().findPaginated(criteria.getPageable(), query, true);
  }

  /**
   * Add sorting to the given query on the given alias
   *
   * @param query to add sorting to
   * @param alias to retrieve columns from for sorting
   * @param sort specification of sorting
   */
  public default void addOrderBy(JPAQuery<CustomerEntity> query, CustomerEntity alias, Sort sort) {

    if (sort != null && sort.isSorted()) {
      Iterator<Order> it = sort.iterator();
      while (it.hasNext()) {
        Order next = it.next();
        switch (next.getProperty()) {
          case "firstName":
            if (next.isAscending()) {
              query.orderBy($(alias.getFirstName()).asc());
            } else {
              query.orderBy($(alias.getFirstName()).desc());
            }
            break;
          case "lastName":
            if (next.isAscending()) {
              query.orderBy($(alias.getLastName()).asc());
            } else {
              query.orderBy($(alias.getLastName()).desc());
            }
            break;
          default:
            throw new IllegalArgumentException("Sorted by the unknown property '" + next.getProperty() + "'");
        }
      }
    }
  }

}