package models

type ContentProduct struct {
	ID        string  `gorm:"type:uuid;default:gen_random_uuid();primaryKey" json:"id"`
	ContentID string  `gorm:"type:uuid;not null" json:"content_id"`
	ProductID string  `gorm:"type:uuid;not null" json:"product_id"`

	// Optional: preload these if needed
	Content  Content  `gorm:"foreignKey:ContentID;constraint:OnDelete:CASCADE;" json:"-"`
	Product  Product  `gorm:"foreignKey:ProductID;constraint:OnDelete:CASCADE;" json:"-"`
}

func (ContentProduct) TableName() string {
	return "content_products"
}