package models

import "time"

type UserAddress struct {
	ID        string    `gorm:"type:uuid;default:gen_random_uuid();primaryKey" json:"id"`
	UserID    string    `gorm:"type:uuid;not null" json:"user_id"`
	Line1     string    `json:"line1"`
	Line2     *string   `json:"line2,omitempty"`
	City      string    `json:"city"`
	State     string    `json:"state"`
	Pincode   string    `json:"pincode"`
	Country   string    `json:"country"`
	IsDefault bool      `gorm:"default:false" json:"is_default"`
	CreatedAt time.Time `gorm:"default:now()" json:"created_at"`
	UpdatedAt time.Time `gorm:"default:now()" json:"updated_at"`
}

func (UserAddress) TableName() string {
	return "user_address"
}
