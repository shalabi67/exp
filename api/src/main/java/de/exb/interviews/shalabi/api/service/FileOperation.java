package de.exb.interviews.shalabi.api.service;

/**
 *  Identifies file operations. this is used in authorization.
 */
public enum FileOperation {
    list,
    Read,
    Write,
    Delete,
    Add
}
