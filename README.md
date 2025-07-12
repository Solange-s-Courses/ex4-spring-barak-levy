Auther: Barak Levy

Email: baraklev.edu.jmc.ac.il

The Video the Internet Project presentation is here: https://www.youtube.com/watch?v=mYwBLiMed-w

To log in as Admin you need to inseart:

admin email: admin@gmail.com

admin password: 123

If the admin wasn't created yet, the program will intialize it with this emaill and password.

The SQL data for ex4 is in the main folder in the file ex4.sql.

# Description about this site:
            
                This website is an online platform for selling electronic products.
                Users can register either as regular users who buy products or as
                company users who sell products to others.

                The site supports full interaction between buyers and sellers, allowing registered companies
                to manage their product listings - adding a new product, edit the product or soft delete it.
                The company can also see all the orders for their products that the users made and view the reviews from users.
                
                The regular users can browse the products, write a review, purchase products and see their orders history.
                He can also add products to the his shopping cart that are saved in the seasion and buy multiply products from the
                same product or multiply different products in the same purchase.

                The platform also includes an admin who responsible for overseeing and moderating the system.
                Admins can manage users and products - like to see all the user and to block access for users, see all the products and soft delete them and also see all the orders that were made -
                this ensure that the platform remains secure and well organized.



# Main pages and their goals:

                Register page - /register: You can create a new regular user who buy products or a company user which sell products.
                Login page - /login: Login to the site with your email and password.
                Home page - /products: Show all the products that are in the site. The regular users can add them to the cart and buy them, and the company and admin users can't.
                My Orders page - /orders/my-orders: This page shows all the orders of the product that you bought. The company can see all the order of their products that other people purchased from them.
                My Product Page - /products/my: This page shows all the company's products – you can go to the add or edit product page which is products/management or soft delete a product. 
                Product Management page - /products/management: In this SPA page the company can create a new product or edit a product.
                Specific Product pages - /products/product-page/{id}: A page for each of the products – here you can see all the details about the products – how much does it cost, description about
                it and more. You also can see here all the revies that other users gave to this product and also regular users who own this product can grade it and write a new review.
                Confirm Order page: orders/confirm: The user confirms or cancel his order of a specific product and see the total price for it.
                Cart page - /cart: Here the regular user can see what products he added to the cart and the total price for them. It is using seassion to store the product in the cart.
                Confirm Order From Cart page - /cart/confirm: The user confirms his order of all the products that he added to the cart or go back.
                User Profile page - users/profile/{id}: The details about the user, like his name, his email and how much money did he spend.
                Admin User Management page - admin/user-management: The admin can here to see all the details about the users and to block access to the site to each of the users (regular or comapny user)
                Admin Product Management page - admin/product-management: Here the admin can see all the products from all the compenies and soft delete any product that he want.
                Admin All Order page - admin/all-orders: Here the Admin see all the orders that were made on the site.
                About page - /about: The details about this site.
