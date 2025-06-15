package models

type ProductMedia struct {
	ID    string `gorm:"column:id;primaryKey;type:uuid;default:gen_random_uuid()" json:"id"`
	URL   string `gorm:"column:url" json:"url"`

	ProductProductMedia []ProductProductMedia `gorm:"foreignKey:ProductMediaID;references:ID" json:"-"`
}

func (ProductMedia) TableName() string {
	return "product_media"
}
