package com.devonfw.app.java.order.orderservice.dataaccess.api.repo;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.devonfw.app.java.order.orderservice.dataaccess.api.ItemEntity;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemSearchCriteriaTo;
import com.devonfw.module.basic.common.api.query.StringSearchConfigTo;
import com.devonfw.module.basic.common.api.query.StringSearchOperator;
import com.devonfw.module.test.common.base.ComponentTest;

/**
 * @author NGASIORO
 *
 */
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class ItemRepositoryTest extends ComponentTest {

  private static final String ITEM_NAME_1 = "Spaghetti bolognese";

  private static final String ITEM_NAME_2 = "Spaghetti carbonara";

  private static final String ITEM_DESCRIPTION = "Italy";

  private static final Double ITEM_PRICE = new Double(250);

  @Inject
  private ItemRepository mSUT;

  @Override
  protected void doTearDown() {

    super.doTearDown();
    this.mSUT.deleteAll();
  }

  @Test
  public void shouldFindAllItems() {

    // given
    addItemToRepository(ITEM_NAME_1, ITEM_DESCRIPTION, ITEM_PRICE);

    // when
    List<ItemEntity> result = this.mSUT.findAll();

    // then
    assertThat(result).hasSize(1);
  }

  @Test
  public void shouldFindItemByNameCriterion() {

    // given
    addItemToRepository(ITEM_NAME_1, ITEM_DESCRIPTION, ITEM_PRICE);
    ItemSearchCriteriaTo criteria = new ItemSearchCriteriaTo();
    criteria.setName(ITEM_NAME_1);

    // when
    List<ItemEntity> result = this.mSUT.findByCriteria(criteria).getContent();

    // then
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getName()).isEqualTo(criteria.getName());
  }

  @Test
  public void shouldFindItemByDescriptionCriterion() {

    // given
    addItemToRepository(ITEM_NAME_1, ITEM_DESCRIPTION, ITEM_PRICE);
    ItemSearchCriteriaTo criteria = new ItemSearchCriteriaTo();
    criteria.setDescription(ITEM_DESCRIPTION);

    // when
    List<ItemEntity> result = this.mSUT.findByCriteria(criteria).getContent();

    // then
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getDescription()).isEqualTo(criteria.getDescription());
  }

  @Test
  public void shouldFindItemByPriceCriterion() {

    // given
    addItemToRepository(ITEM_NAME_1, ITEM_DESCRIPTION, ITEM_PRICE);
    ItemSearchCriteriaTo criteria = new ItemSearchCriteriaTo();
    criteria.setPrice(ITEM_PRICE);

    // when
    List<ItemEntity> result = this.mSUT.findByCriteria(criteria).getContent();

    // then
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getPrice()).isEqualTo(criteria.getPrice());
  }

  @Test
  public void shouldFindItemByCriteria() {

    // given
    addItemToRepository(ITEM_NAME_1, ITEM_DESCRIPTION, ITEM_PRICE);
    ItemSearchCriteriaTo criteria = new ItemSearchCriteriaTo();
    criteria.setName(ITEM_NAME_1);
    criteria.setDescription(ITEM_DESCRIPTION);
    criteria.setPrice(ITEM_PRICE);

    // when
    List<ItemEntity> result = this.mSUT.findByCriteria(criteria).getContent();

    // then
    assertThat(result).hasSize(1);

    ItemEntity item = result.get(0);
    assertThat(item.getName()).isEqualTo(criteria.getName());
    assertThat(item.getDescription()).isEqualTo(criteria.getDescription());
    assertThat(item.getPrice()).isEqualTo(criteria.getPrice());
  }

  @Test
  public void shouldFindAndSortItemsByName() {

    // given
    addItemToRepository(ITEM_NAME_1, ITEM_DESCRIPTION, ITEM_PRICE);
    addItemToRepository(ITEM_NAME_2, ITEM_DESCRIPTION, ITEM_PRICE);

    Pageable pageable = PageRequest.of(0, 20, Sort.by("name").descending());
    ItemSearchCriteriaTo criteria = new ItemSearchCriteriaTo();
    criteria.setDescription(ITEM_DESCRIPTION);
    criteria.setPageable(pageable);

    // when
    List<ItemEntity> result = this.mSUT.findByCriteria(criteria).getContent();

    // then
    assertThat(result).hasSize(2);

    String firstItemName = result.get(0).getName();
    String secondItemName = result.get(1).getName();
    assertThat(firstItemName.compareTo(secondItemName)).isGreaterThan(0);
  }

  @Test
  public void shouldFindAndSortItemsByDescription() {

    // given
    addItemToRepository(ITEM_NAME_1, ITEM_DESCRIPTION, ITEM_PRICE);
    addItemToRepository(ITEM_NAME_2, "Poland", ITEM_PRICE);

    Pageable pageable = PageRequest.of(0, 20, Sort.by("description").descending());
    ItemSearchCriteriaTo criteria = new ItemSearchCriteriaTo();
    criteria.setPrice(ITEM_PRICE);
    criteria.setPageable(pageable);

    // when
    List<ItemEntity> result = this.mSUT.findByCriteria(criteria).getContent();

    // then
    assertThat(result).hasSize(2);

    String firstItemDesc = result.get(0).getDescription();
    String secondItemDesc = result.get(1).getDescription();
    assertThat(firstItemDesc.compareTo(secondItemDesc)).isGreaterThan(0);
  }

  @Test
  public void shouldFindAndSortItemsByPrice() {

    // given
    addItemToRepository(ITEM_NAME_1, ITEM_DESCRIPTION, ITEM_PRICE);
    addItemToRepository(ITEM_NAME_2, ITEM_DESCRIPTION, new Double(9999));

    Pageable pageable = PageRequest.of(0, 20, Sort.by("price").descending());
    ItemSearchCriteriaTo criteria = new ItemSearchCriteriaTo();
    criteria.setDescription(ITEM_DESCRIPTION);
    criteria.setPageable(pageable);

    // when
    List<ItemEntity> result = this.mSUT.findByCriteria(criteria).getContent();

    // then
    assertThat(result).hasSize(2);

    Double firstItemPrice = result.get(0).getPrice();
    Double secondItemPrice = result.get(1).getPrice();
    assertThat(firstItemPrice).isGreaterThan(secondItemPrice);
  }

  @Test
  public void shouldFindItemByName() {

    // given
    addItemToRepository(ITEM_NAME_1, ITEM_DESCRIPTION, ITEM_PRICE);

    // when
    ItemEntity result = this.mSUT.findByName(ITEM_NAME_1);

    // then
    assertThat(result.getName()).isEqualTo(ITEM_NAME_1);
  }

  @Test
  public void shouldFindItemsWithNameLikeArgument() {

    // given
    addItemToRepository(ITEM_NAME_1, ITEM_DESCRIPTION, ITEM_PRICE);
    addItemToRepository(ITEM_NAME_2, ITEM_DESCRIPTION, ITEM_PRICE);

    Pageable pageable = PageRequest.of(0, 20, Sort.by("name").ascending());
    ItemSearchCriteriaTo criteria = new ItemSearchCriteriaTo();
    criteria.setName("Spaghetti");
    StringSearchOperator syntax = StringSearchOperator.LIKE;
    StringSearchConfigTo nameOption = StringSearchConfigTo.of(syntax);
    nameOption.setIgnoreCase(false);
    nameOption.setMatchSubstring(true);
    criteria.setNameOption(nameOption);
    criteria.setPageable(pageable);

    // when
    List<ItemEntity> result = this.mSUT.findByCriteria(criteria).getContent();

    // then
    assertThat(result).hasSize(2);

    String firstItemName = result.get(0).getName();
    String secondItemName = result.get(1).getName();
    assertThat(firstItemName.compareTo(secondItemName)).isLessThan(0);
  }

  @Test
  public void shouldNotFindItemsWithNameLikeArgument() {

    // given
    addItemToRepository(ITEM_NAME_1, ITEM_DESCRIPTION, ITEM_PRICE);
    addItemToRepository(ITEM_NAME_2, ITEM_DESCRIPTION, ITEM_PRICE);

    Pageable pageable = PageRequest.of(0, 20, Sort.by("name").ascending());
    ItemSearchCriteriaTo criteria = new ItemSearchCriteriaTo();
    criteria.setName("spaghetti");
    StringSearchOperator syntax = StringSearchOperator.LIKE;
    StringSearchConfigTo nameOption = StringSearchConfigTo.of(syntax);
    nameOption.setIgnoreCase(false);
    nameOption.setMatchSubstring(true);
    criteria.setNameOption(nameOption);
    criteria.setPageable(pageable);

    // when
    List<ItemEntity> result = this.mSUT.findByCriteria(criteria).getContent();

    // then
    assertThat(result).hasSize(0);
  }

  @Test
  public void shouldUpdatePriceForItemWithGivenName() {

    // given
    addItemToRepository(ITEM_NAME_1, ITEM_DESCRIPTION, ITEM_PRICE);
    double price = 999.99;

    // when
    ItemEntity item = this.mSUT.findByName(ITEM_NAME_1);
    item.setPrice(price);
    ItemEntity result = this.mSUT.save(item);

    // then
    assertThat(result.getId()).isEqualTo(item.getId());
    assertThat(result.getName()).isEqualTo(item.getName());
    assertThat(result.getDescription()).isEqualTo(item.getDescription());
    assertThat(result.getPrice()).isEqualTo(price);

    Optional<ItemEntity> foundItem = this.mSUT.findById(item.getId());
    assertThat(foundItem).isPresent();
    assertThat(foundItem.get().getPrice()).isEqualTo(price);
  }

  private void addItemToRepository(String name, String description, Double price) {

    ItemEntity item = new ItemEntity();
    item.setName(name);
    item.setDescription(description);
    item.setPrice(price);
    this.mSUT.save(item);
  }

}
