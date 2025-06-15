package models

type Brand struct {
	ID               string   `gorm:"type:uuid;default:gen_random_uuid();primaryKey" json:"id"`
	Name             string   `gorm:"type:varchar(50);not null" json:"name"`
	Email            *string  `json:"email,omitempty"`
	GstinNumber      *string  `gorm:"type:varchar(15)" json:"gstin_number,omitempty"`
	PanNumber        string   `gorm:"type:varchar(15);default:''" json:"pan_number"`
	Description      *string  `json:"description,omitempty"`
	LogoURL          *string  `gorm:"default:''" json:"logo_url,omitempty"`
	BrandSlug         string  `gorm:"unique;not null" json:"brand_slug"`
	MaxDeliveryDay   *int     `gorm:"default:10" json:"max_delivery_day,omitempty"`
	MinDeliveryDay   *int     `gorm:"default:5" json:"min_delivery_day,omitempty"`
	Phone            *int     `gorm:"type:smallint" json:"phone,omitempty"`
	ViewCount        *int     `json:"view_count,omitempty"`
	LikeCount        *int     `json:"like_count,omitempty"`
	ShareCount       *int     `json:"share_count,omitempty"`
	Location         *string  `json:"location,omitempty"`
}

func (Brand) TableName() string {
	return "brand"
}
