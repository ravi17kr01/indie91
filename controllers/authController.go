package controllers

import (
	"fmt"
	// "supabase-fiber-app/config"
	// "supabase-fiber-app/models"
	// "supabase-fiber-app/utils"

	"github.com/gofiber/fiber/v2"
)

func SignUp(c *fiber.Ctx) {
	fmt.Println("Signup Controller")
	// var user models.App
	// if err := c.BodyParser(&user); err != nil {
	// 	return c.Status(400).JSON(fiber.Map{"error": "Invalid request"})
	// }

	// hashedPwd, err := utils.HashPassword(user.Password)
	// if err != nil {
	// 	return c.Status(500).JSON(fiber.Map{"error": "Error hashing password"})
	// }
	// user.Password = hashedPwd

	// if err := config.DB.Create(&user).Error; err != nil {
	// 	return c.Status(400).JSON(fiber.Map{"error": "User already exists"})
	// }

	// return c.JSON(fiber.Map{"message": "Signup successful"})
}

func Login(c *fiber.Ctx) {
	fmt.Println("Login Controller")
	// req := new(models.User)
	// if err := c.BodyParser(req); err != nil {
	// 	return c.Status(400).JSON(fiber.Map{"error": "Invalid request"})
	// }

	// var user models.User
	// if err := config.DB.Where("email = ?", req.Email).First(&user).Error; err != nil {
	// 	return c.Status(400).JSON(fiber.Map{"error": "User not found"})
	// }

	// if !utils.CheckPasswordHash(req.Password, user.Password) {
	// 	return c.Status(401).JSON(fiber.Map{"error": "Invalid credentials"})
	// }

	// token, err := utils.GenerateJWT(user.Email)
	// if err != nil {
	// 	return c.Status(500).JSON(fiber.Map{"error": "Token generation failed"})
	// }

	// return c.JSON(fiber.Map{"token": token})
}
