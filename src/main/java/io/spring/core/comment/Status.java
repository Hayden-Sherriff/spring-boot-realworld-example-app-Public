package io.spring.core.comment;

public enum Status {

    PUBLISHED,   // Visible to everyone
    DRAFT,       // Saved but not published (optional)
//    HIDDEN,      // Hidden by author or auto-moderation
//    FLAGGED,     // Reported by users
    REMOVED      // Removed by admin/moderator (soft delete)
}