package com.mangazo.ecommerce;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mangazo.ecommerce.dao.*;
import com.mangazo.ecommerce.entity.Comuna;
import com.mangazo.ecommerce.entity.Product;
import com.mangazo.ecommerce.entity.ProductCategory;
import com.mangazo.ecommerce.entity.Region;
import com.mangazo.ecommerce.service.CheckoutServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@SpringBootTest
@Transactional
public class EntityDaoTest {

    private static MockHttpServletRequest mockHttpServletRequest;

    @PersistenceContext
    private EntityManager entityManager;

    @Mock
    CheckoutServiceImpl checkoutService;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private ComunaRepository comunaRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${sql.script.create.product.category}")
    private String sqlAddProductCategory;

    @Value("${sql.script.create.product}")
    private String sqlAddProduct;

    @Value("${sql.script.delete.product}")
    private String sqlDeleteProduct;

    @Value("${sql.script.delete.product.category}")
    private String sqlDeleteProductCategory;

    @Value("${sql.script.create.region}")
    private String sqlAddRegion;

    @Value("${sql.script.create.comuna}")
    private String sqlAddComuna;

    @Value("${sql.script.delete.comuna}")
    private String sqlDeleteComuna;

    @Value("${sql.script.delete.region}")
    private String sqlDeleteRegion;

    @BeforeEach
    void setup() {
        jdbc.execute(sqlAddProductCategory);
        jdbc.execute(sqlAddProduct);
        jdbc.execute(sqlAddRegion);
        jdbc.execute(sqlAddComuna);
    }

    @AfterEach
    void tearDown() {
        jdbc.execute(sqlDeleteProduct);
        jdbc.execute(sqlDeleteProductCategory);
        jdbc.execute(sqlDeleteComuna);
        jdbc.execute(sqlDeleteRegion);

        jdbc.execute("DELETE FROM orders");
        jdbc.execute("DELETE FROM address");
        jdbc.execute("DELETE FROM customer");


    }

    @Test
    @Order(1)
    @DisplayName("ComunaRepository: Find all comunas")
    void testComunaRepositoryFindAll() {
        assertNotNull(comunaRepository.findAll(), "Comuna should be valid");
        assertEquals(5, comunaRepository.findAll().size());
    }

    @Test
    @Order(2)
    @DisplayName("ComunaRepository: Find by id")
    void testComunaRepositoryFindById() {
        Optional<Comuna> comuna = comunaRepository.findById(1);
        assertTrue(comuna.isPresent());
        assertEquals(1, comuna.get().getId());
        assertEquals("Arica", comuna.get().getNombre());
    }

    @Test
    @Order(3)
    @DisplayName("ComunaRepository: Find All Comunas By Region Id")
    void testComunaRepositoryFindAllByRegionId() {
        List<Comuna> comunas = comunaRepository.findByRegionId(1);
        assertNotNull(comunas, "Comunas should be valid");
        assertEquals(2, comunas.size());
        assertEquals("Arica", comunas.get(0).getNombre());
        assertEquals("Camarones", comunas.get(1).getNombre());

        comunas = comunaRepository.findByRegionId(2);
        assertNotNull(comunas, "Comunas should be valid");
        assertEquals(3, comunas.size());
        assertEquals("Putre", comunas.get(0).getNombre());
        assertEquals("General Lagos", comunas.get(1).getNombre());
        assertEquals("Iquique", comunas.get(2).getNombre());
    }

    @Test
    @Order(4)
    @DisplayName("ComunaRepository: Find All Comunas By Region Id Empty Response")
    void testComunaRepositoryFindAllByRegionIdEmptyResponse() {
        List<Comuna> comunas = comunaRepository.findByRegionId(0);
        assertTrue(comunas.isEmpty(), "Comunas list should be empty");
    }

    @Test
    @Order(5)
    @DisplayName("ProductCategoryRepository: Find by category name")
    void testProductCategoryRepositoryFindByCategoryName() {
        ProductCategory productCategory = productCategoryRepository.findByCategoryName("manga");
        assertNotNull(productCategory, "ProductCategory should be valid");
        assertEquals("manga", productCategory.getCategoryName());
        assertEquals(1, productCategory.getId());
    }

    @Test
    @Order(6)
    @DisplayName("ProductCategoryRepository: Find All categories")
    void testProductCategoryRepositoryFindAll() {
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertNotNull(productCategoryList, "ProductCategory should be valid");
        assertEquals(2, productCategoryList.size());
    }

    @Test
    @Order(7)
    @DisplayName("ProductCategoryRepository: Find By ID")
    void testProductCategoryRepositoryFindById() {
        Optional<ProductCategory> productCategory = productCategoryRepository.findById(2L);
        assertTrue(productCategory.isPresent());
        assertEquals("comic", productCategory.get().getCategoryName());
    }

    @Test
    @Order(8)
    @DisplayName("ProductCategoryRepository: Find By ID Empty Response")
    void testProductCategoryRepositoryFindByIdEmptyResponse() {
        Optional<ProductCategory> productCategory = productCategoryRepository.findById(0L);
        assertFalse(productCategory.isPresent());
    }

    @Test
    @Order(9)
    @DisplayName("ProductRepository: Find By ID")
    void testProductRepositoryFindById() {
        Optional<Product> product = productRepository.findById(1L);
        assertTrue(product.isPresent());
        assertNotNull(product, "Product should be valid");
    }

    @Test
    @Order(10)
    @DisplayName("ProductRepository: Find By ID")
    void testProductRepositoryFindByIdEmptyResponse() {
        Optional<Product> product = productRepository.findById(0L);
        assertFalse(product.isPresent());
    }

    @Test
    @Order(11)
    @DisplayName("ProductRepository: Find All")
    void testProductRepositoryFindAll() {
        assertNotNull(productRepository.findAll(), "Product list should be valid");
        assertEquals(2, productRepository.findAll().size());
    }

    @Test
    @Order(12)
    @DisplayName("ProductRepository: Find All By Category ID")
    void testProductRepositoryFindAllByCategoryId() {

        Optional<Product> product = productRepository.findById(1L);
        assertTrue(product.isPresent());
        Product product1 = product.get();
        Page<Product> productsPage = productRepository.findByCategoryId(product1.getCategory().getId(), Pageable.unpaged());
        assertNotNull(productsPage, "Product should be valid");
        assertEquals(1, productsPage.getTotalElements());

    }

    @Test
    @Order(13)
    @DisplayName("ProductRepository: Find All By Category ID Empty Response")
    void testProductRepositoryFindAllByCategoryIdEmptyResponse() {

        Optional<Product> product = productRepository.findById(0L);
        assertFalse(product.isPresent());

    }

    @Test
    @Order(14)
    @DisplayName("ProductRepository: Find All By Name Containing Key Word")
    void testProductRepositoryFindAllByNameContainingKeyWord() {

        Page<Product> productPage = productRepository.findByNameContaining("comic", Pageable.unpaged());
        assertFalse(productPage.isEmpty(), "Product page should not be empty");
        assertEquals(1, productPage.getTotalElements());

        Page<Product> productPage2 = productRepository.findByNameContaining("product", Pageable.unpaged());
        assertFalse(productPage.isEmpty(), "Product page should not be empty");
        assertEquals(2, productPage2.getTotalElements());

        List<Product> products = productPage2.getContent();
        assertEquals("manga product", products.get(0).getName());
        assertEquals("comic product", products.get(1).getName());
    }

    @Test
    @Order(15)
    @DisplayName("ProductRepository: Find All By Name Containing Key Word Empty Response")
    void testProductRepositoryFindAllByNameContainingKeyWordEmptyResponse() {

        Page<Product> productPage = productRepository.findByNameContaining("test", Pageable.unpaged());
        assertTrue(productPage.isEmpty(), "Product page should be empty");
        assertEquals(0, productPage.getTotalElements());

    }

    @Test
    @Order(16)
    @DisplayName("ProductRepository: Find All Pageable")
    void testProductRepositoryFindAllPageable() {
        Page<Product> productPage = productRepository.findAll(Pageable.unpaged());
        assertNotNull(productRepository.findAll(), "Product list should be valid");
        assertEquals(2, productRepository.findAll().size(), "Product list should have 2 elements");
    }

    @Test
    @Order(17)
    @DisplayName("RegionRepository: Find All")
    void testRegionRepositoryFindAll() {
        assertNotNull(regionRepository.findAll(), "Region should be valid");
        assertEquals(2, regionRepository.findAll().size(), "2 Region On test db");
    }

    @Test
    @Order(18)
    @DisplayName("RegionRepository: Find By ID")
    void testRegionRepositoryFindById() {
        Optional<Region> region = regionRepository.findById(1);
        assertTrue(region.isPresent());
        assertEquals(1, region.get().getId());
        assertEquals("Arica y Parinacota", region.get().getNombre());

        Optional<Region> region2 = regionRepository.findById(2);
        assertTrue(region2.isPresent());
        assertEquals(2, region2.get().getId());
        assertEquals("Tarapaca", region2.get().getNombre());

    }

    @Test
    @Order(18)
    @DisplayName("RegionRepository: Find By ID Empty Response")
    void testRegionRepositoryFindByIdEmptyResponse() {
        Optional<Region> region = regionRepository.findById(0);
        assertFalse(region.isPresent());
    }

    @Test
    @Order(19)
    @DisplayName("OrderRepository: Find By Customer Email Order By Date Created Desc")
    @Sql({"/insertOrders.sql"})
    @WithMockUser(username = "jai.vicencio@duocuc.cl", roles = "USER")
    void testOrderRepositoryFindByCustomerEmailOrderByDateCreatedDesc() throws Exception {
        mockMvc.perform(get("/api/orders/search/findByCustomerEmailOrderByDateCreatedDesc")
                        .param("email", "jai.vicencio@duocuc.cl")
                        .param("page", "0")
                        .param("size", "20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.orders", hasSize(2)));
    }

    @Test
    @Order(20)
    @DisplayName("OrderRepository: Get All")
    @Sql({"/insertOrders.sql"})
    @WithMockUser(username = "jai.vicencio@duocuc.cl", roles = "USER")
    void testOrderRepositoryGetAll() throws Exception {
        mockMvc.perform(get("/api/orders")
                        .param("page", "0")
                        .param("size", "20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.orders", hasSize(2)));
    }

    @Test
    @Order(21)
    @DisplayName("OrderRepository: Find All")
    @Sql({"/insertOrders.sql"})
    @WithMockUser(username = "jai.vicencio@duocuc.cl", roles = "USER")
    void testOrderRepositoryFindAll() throws Exception {
        assertNotNull(orderRepository.findAll(), "Orders should be valid");
        assertEquals(2, orderRepository.findAll().size());
    }

    @Test
    @Order(22)
    @DisplayName("OrderRepository: Find By ID")
    @Sql({"/insertOrders.sql"})
    @WithMockUser(username = "jai.vicencio@duocuc.cl", roles = "USER")
    void testOrderRepositoryFindById() throws Exception {
        Optional<com.mangazo.ecommerce.entity.Order> order = orderRepository.findById(1L);
        assertTrue(order.isPresent());
        assertEquals("tracking123", order.get().getOrderTrackingNumber());
    }

    @Test
    @Order(23)
    @DisplayName("OrderRepository: Find By ID Empty Response")
    @Sql({"/insertOrders.sql"})
    @WithMockUser(username = "jai.vicencio@duocuc.cl", roles = "USER")
    void testOrderRepositoryFindByIdEmptyResponse() throws Exception {
        Optional<com.mangazo.ecommerce.entity.Order> order = orderRepository.findById(0L);
        assertFalse(order.isPresent(), "Order ID = '0' should not be valid");
    }

    @Test
    @Order(24)
    @DisplayName("OrderRepository: Find By Customer Email Order By Date Created Desc")
    @Sql({"/insertOrders.sql"})
    @WithMockUser(username = "jai.vicencio@duocuc.cl", roles = "USER")
    void testOrderFindByCustomerEmailOrderByDateCreatedDesc() throws Exception {
        assertNotNull(orderRepository.findByCustomerEmailOrderByDateCreatedDesc("jai.vicencio@duocuc.cl", Pageable.unpaged()), "Order should be valid");
        assertEquals(2, orderRepository.findByCustomerEmailOrderByDateCreatedDesc("jai.vicencio@duocuc.cl", Pageable.unpaged()).getSize());
    }

    @Test
    @Order(25)
    @DisplayName("OrderRepository: Find By Customer Email Order By Date Created Desc Empty Response")
    @Sql({"/insertOrders.sql"})
    @WithMockUser(username = "jai.vicencio@duocuc.cl", roles = "USER")
    void testOrderFindByCustomerEmailOrderByDateCreatedDescEmptyResponse() throws Exception {
        assertEquals(0, orderRepository.findByCustomerEmailOrderByDateCreatedDesc("email.no.registrado@duocuc.cl", Pageable.unpaged()).getSize());
    }










}
