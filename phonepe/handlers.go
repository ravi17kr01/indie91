package phonepe

import (
	"fmt"
	"github.com/gofiber/fiber/v2"
)

func PhonePayHandler(c *fiber.Ctx) error {
    var req PayRequest
    if err := c.BodyParser(&req); err != nil {
        fmt.Println("Error from payment handler>>>>>>>>>>>>>", err)
        return c.Status(400).JSON(fiber.Map{"error": "Invalid request"})
    }

    resp, err := InitiatePayment(req)
    if err != nil {
        return c.Status(500).JSON(fiber.Map{"error": err.Error()})
    }
    return c.JSON(resp)
}
