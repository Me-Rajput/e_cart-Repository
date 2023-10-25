use e_kart_application

DELIMITER $$
create procedure get_brand_detail(IN id int)
begin
   select * from branddetails where product_id=id;
end$$
DELIMITER ;

call get_brand_detail(4002)

--------------------------------------------------------------------------------------------------------------
/*stored procedure with optional parameter*/
DELIMITER $$
create procedure test(IN query_string varchar(50))
begin

declare conditions varchar(1000);
declare sql_query varchar(1000);

set @conditions=query_string;    
set @sql_query=concat('select * from branddetails ',@conditions);

prepare stmt1 from @sql_query;
execute stmt1;
deallocate prepare stmt1;
end $$
DELIMITER ;

call test('')

--------------------------------------------------------------------------------------------------------------

DELIMITER $$
create procedure get_top_product_from_each_group(IN productId long, IN optional_query_string varchar(10))
begin

	declare prodId long;
	declare optional_query varchar(10);
    declare sql_query varchar(2000);
    
    set @prodId=productId;
    set @optional_query=optional_query_string;
    set @sql_query=concat(
				'select * from 
			(
			select prod.main_category_id, subprod.sub_productid, subsubprod.subsub_id,  ROW_NUMBER() over (partition by subprod.sub_productid order by brnd.price) AS r_no,  
			brnd.product_id, subprod.product_name ,  subprod.status,  subsubprod.brande_name, 
			brnd.color, brnd.is_available, brnd.price, brnd.size, brnd.description, brnd.imageurl, brnd.new_arrival, 
			brnd.star_rating, brnd.mrp, brnd.is_top_product

			from productdetail prod , subproductdetails subprod, subsubproductdetails subsubprod , branddetails brnd 
			where prod.main_category_id=subprod.product_id
			and subprod.sub_productid=subsubprod.sub_id
			and subsubprod.subsub_id=brnd.sub_sub_id
			and  prod.main_category_id=',@prodId,
			' and brnd.is_top_product=1
			) t
			where t.r_no=1 ',@optional_query
          );
    
    prepare stmt1 from  @sql_query;
    execute stmt1;
    deallocate prepare stmt1;
	
end $$
DELIMITER ;

call get_top_products(1002,'limit 2')

------------------------------------------------------------------------------------------------------------------------------------------------
DELIMITER $$
  CREATE PROCEDURE get_all_top_product_categorywise(IN maincategoryid long)
    begin
      declare id long;
      declare query_string varchar(1000);
      
      set @id=maincategoryid;
      set @query_string=concat(
			'select prod.main_category_id, subprod.sub_productid, subsubprod.subsub_id, brnd.product_id, subprod.product_name ,  subprod.status,  subsubprod.brande_name, 
			brnd.color, brnd.is_available, brnd.price, brnd.size, brnd.description, brnd.imageurl, brnd.new_arrival, 
			brnd.star_rating, brnd.mrp, brnd.is_top_product
			from productdetail prod , subproductdetails subprod, subsubproductdetails subsubprod , branddetails brnd 
			where prod.main_category_id=subprod.product_id
			and subprod.sub_productid=subsubprod.sub_id
			and subsubprod.subsub_id=brnd.sub_sub_id
			and  prod.main_category_id=',@id,
			' and brnd.is_top_product=1'
      );
      prepare stmt1 from @query_string;
      execute stmt1;
      deallocate prepare stmt1;
    end $$
DELIMITER ;

-------------------------------------------------------------------------------------------------------------------------------------------------------
DELIMITER $$
   create procedure get_sorted_and_filtered_product(IN productId long, IN sorting_query varchar(30), IN filter_query varchar(500), IN star_filter_query varchar(100), IN price_filter_query varchar(100))
	  begin
		declare id long;
        declare sort_query varchar(30);
        declare query_string varchar(1000);
        declare filter_query_str varchar(500);
        declare star_filter_query_str varchar(100);
        declare price_filter_query_str varchar(100);
        
        set @id=productId;
        set @sort_query=sorting_query;
        set @filter_query_str=filter_query;
        set @price_filter_query_str=price_filter_query;
        set @star_filter_query_str=star_filter_query;
        set @query_string=concat(
            'select sub.product_name, subsub.sub_id,subsub.subsub_id, brnd.product_id, subsub.brande_name, brnd.color, brnd.is_available, brnd.price, brnd.size,
			 brnd.description, brnd.imageurl, brnd.new_arrival, brnd.star_rating, brnd.mrp 
			 from subsubproductdetails subsub, branddetails brnd, subproductdetails sub
			 where sub.sub_productid=subsub.sub_id
			 and subsub.subsub_id=brnd.sub_sub_id 
			 and subsub.sub_id=',@id,' ', @price_filter_query_str,' ',@filter_query_str,' ',@star_filter_query_str,' ',@sort_query
          );
        prepare stmt1 from  @query_string;
        execute stmt1;
        deallocate prepare stmt1; 
	  end $$
DELIMITER ;

-------------------------------------------------------------------------------------------------------------------------------------------------------------------
DELIMITER $$
create procedure get_filtered_orderlist(IN customerID long, IN filterQuery varchar(50))
  begin
    declare custId long;
    declare filterVal varchar(50);
    declare query_str varchar(500);
    
    set @custId=customerID;
    set @filterVal=filterQuery;
    set @query_str=concat(
      'select order_id, delivery_address, delivery_date, description, imageurl, order_date, productid, quantity, total_price, unit_price, customer_id,
       order_status, order_status_value
	   from orderdetails
	   where customer_id=',@custId,@filterVal,' order by order_date desc'
    );
    
    prepare stmt1 from @query_str;
    execute stmt1;
    deallocate prepare stmt1;
  end $$
DELIMITER ;