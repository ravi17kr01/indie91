package models

type Tag struct {
	ID          string     `gorm:"type:uuid;default:gen_random_uuid();primaryKey" json:"id"`
	Name        string     `gorm:"type:varchar(50);not null" json:"name"`
	Description *string    `gorm:"type:varchar(100)" json:"description"`
	Content     []Content  `gorm:"many2many:content_tags;" json:"content,omitempty"`
}

func (Tag) TableName() string {
	return "tag"
}