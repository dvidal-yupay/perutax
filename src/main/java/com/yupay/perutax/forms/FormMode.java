package com.yupay.perutax.forms;

/**
 * The card form modality defines the behavior
 * (editability) of the fields.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public enum FormMode {
    /**
     * The read only mode is to show a value
     * but not edit it.
     */
    READ_ONLY,
    /**
     * The editor allows to edit a value.
     * However, some fields shouldn't be
     * editable, like IDs.
     */
    EDITOR,
    /**
     * The creator mode, allows to edit every
     * field because the item is being created.
     */
    CREATOR
}
