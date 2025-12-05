// java
package com.BIT.BCMS.entities;

public enum Permission {

    // ==========================
    // 1. ADMIN & SYSTEM CONTROLS
    // ==========================
         // Delete users
    ROLE_MANAGE,          // Create/View/Update/Delete/Assign roles
    ADMIN_SYSTEM_LOGS_VIEW,// View server logs
    USERS_VIEW_ALL,
    // ==========================
    // 2. DEPARTMENT / ORGANIZATION
    // ==========================
    DEPARTMENT_MANAGE,          // Admin: Create/View/Edit/Delete Departments

    // ==========================
    // 2. CATEGORY / ORGANIZATION
    // ==========================
    CATEGORY_MANAGE,          // Admin: Create/View/Edit/Delete Departments

    // ==========================
    // 3. OFFICER COMPLAINT MANAGEMENT
    // ==========================
    COMPLAINT_VIEW_ALL,  // See complaints from ALL users
    COMPLAINT_DELETE_ANY,       // Delete inappropriate complaints
    COMPLAINT_UPDATE_STATUS,    // Change status (Pending -> Resolved)
    COMPLAINT_ADD_INTERNAL_NOTE,// Add hidden notes for other officers

    // ==========================
    // 4. MY PROFILE (Self Service)
    // ==========================
    USER_DELETE,             // Delete my own account
    USER_UPDATE,            // Update my own phone
    USER_CREATE,            // Create Account
    USER_PROFILE_VIEW,      // View my profile details
    // ==========================
    // 5. MY COMPLAINTS (Self Service)
    // ==========================
    COMPLAINT_CREATE, // File a new complaint
    COMPLAINT_VIEW_OWN,   // View my complaint details
    COMPLAINT_VIEW_HISTORY,  // View list of complaints I created
    COMPLAINT_UPDATE_CONTENT,// Edit my complaint text (if allowed)
    COMPLAINT_DELETE,        // Delete my complaint (if drafted)
    COMPLAINT_ADD_COMMENT,// Add a public comment to the thread
    COMPLAINT_ATTACH_FILE,

    ORG_MANAGE // Manage organization details
}