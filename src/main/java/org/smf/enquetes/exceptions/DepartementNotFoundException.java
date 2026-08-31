package org.smf.enquetes.exceptions;

import java.util.UUID;

public class DepartementNotFoundException extends RuntimeException {
  public DepartementNotFoundException(UUID id) {
    super("Département non trouvé avec l'id : " + id);
  }
}