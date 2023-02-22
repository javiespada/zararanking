package com.inditex.zboost.service;

import com.inditex.zboost.entity.Product;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public ProductServiceImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> findProducts(Optional<List<String>> categories) {
        /**
         * TODO: EJERCICIO 1.a) Utiliza el jdbcTemplate para recuperar productos por sus categorias. Si dicho filtro
         * no esta presente, recupera TODOS los productos del catalogo.
         *
         * Recuerda que el filtrado de categorias debe ser CASE-INSENSITIVE: la busqueda debe devolver los mismos resultados
         * filtrando por 'dresses', 'Dresses' o 'dRessES', por ejemplo.
         *
         * Para realizar filtrados en la clausula WHERE, recuerda que no es buena practica hacer un append directo de los
         * valores, si no que debes hacer uso de PreparedStatements para prevenir inyecciones de SQL. Ejemplo:
         *
         * "WHERE name = " + person.getName() + " AND ..." ==> MAL
         * "WHERE name = :name AND ..." ==> BIEN
         * 
         *  Pista: A la hora de filtrar, pasar los valores a mayúsculas o minúsculas. Ejemplo: Uso de la función SQL upper().
         */
        Map<String, Object> params = new HashMap<>();

        String sql = """
            SELECT * 
            FROM Products
        """;

        if (categories.isPresent()) {
            List<String> categoryList = categories.get();
            sql += "WHERE Lower (category) IN (";
            for (int i = 0; i < categoryList.size() -1; i++){
                sql += "':category" + i +"', ";
                params.put("category"+i, categoryList.get(i));
            }
            sql += "':category" + categoryList.size() +"')";
            params.put("category"+categoryList.size(), categoryList.get(categoryList.size()));
        }
        sql += ";";

        return jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Product.class));
    }

    @Override
    public List<String> findProductCategories() {
        /**
         * TODO: EJERCICIO 1.b) Recupera las distintas categorias de los productos disponibles.
         */

        String sql = """
            SELECT DISTINCT Category
            FROM Products;
        """;

        return jdbcTemplate.queryForList(sql, (SqlParameterSource) null, String.class);
    }


}
