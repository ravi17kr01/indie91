package main

import (
	"log"
	"os"
	"supabase-fiber-app/config"
	"supabase-fiber-app/routes"
	"github.com/gofiber/fiber/v2"
	"github.com/gofiber/fiber/v2/middleware/cors"
)

func main() {
	app := fiber.New()

	// Apply CORS middleware globally
	app.Use(cors.New(cors.Config{
		AllowOrigins:     "https://indie91.vercel.app, http://localhost:3000",
		AllowHeaders:     "Origin, Content-Type, Accept, Authorization",
		AllowMethods:     "GET, POST, PUT, DELETE, OPTIONS",
		ExposeHeaders:    "Content-Length",
		AllowCredentials: true,
	}))

	config.InitDB()

	// ✅ Init Twilio
	accountSID := os.Getenv("TWILIO_SID")
	authToken := os.Getenv("TWILIO_AUTH_TOKEN")

	if accountSID == "" || authToken == "" {
		log.Println("Missing Twilio credentials")
	}

	log.Println("TWILIO_SID:", os.Getenv("TWILIO_SID"))
    log.Println("TWILIO_AUTH_TOKEN:", os.Getenv("TWILIO_AUTH_TOKEN"))

	config.InitTwilio(accountSID, authToken)
	// config.InitRedis()

	// routes.RegisterAuthRoutes(app)
	routes.RegisterUserRoutes(app)
	routes.RegisterProductRoutes(app)
	routes.RegisterBrandRoutes(app)
	routes.RegisterCartItemRoutes(app)
	routes.RegisterContentRoutes(app)
	routes.RegisterContentProductRoutes(app)
	routes.RegisterInfluencerRoutes(app)
	routes.RegisterOrderRoutes(app)
	routes.RegisterPaymentRoutes(app)
	routes.RegisterProductImageRoutes(app)
	routes.RegisterProductMediaRoutes(app)
	routes.RegisterProductProductMediaRoutes(app)
	routes.RegisterTagRoutes(app)
	routes.RegisterUserProfileRoutes(app)
	routes.RegisterOtpRoutes(app)
	routes.RegisterUserAddressRoutes(app)
	routes.RegisterProductVariantRoutes(app)

	// Get port from environment variable, fallback to 3000 if not set
	port := os.Getenv("PORT")
	if port == "" {
		port = "3000"
	}

	log.Printf("Starting server on port %s...", port)

	err := app.Listen(":3000")
	if err != nil {
		log.Fatal(err)
	}
	//log.Fatal(app.Listen(":" + port))
}
