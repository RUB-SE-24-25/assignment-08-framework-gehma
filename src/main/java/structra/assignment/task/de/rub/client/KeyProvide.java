//matrickel nummern :
// Johanna Lütke Entrup - 10802306870
//Melanie Waldikowski -  108023211670
//Leonhard Herlitz -   108023208379

package structra.assignment.task.de.rub.client;

import structra.assignment.framework.llm.KeyProvider;

public class KeyProvide implements KeyProvider {

    public KeyProvide() {
        getApiKey();
    }

    public String getApiKey() {
        return "structra-1343abnc-dGhpcyBpcyBub3Qgb3VyIGFwaSBrZXksIG5pY2UgdHJ5IHRobyA6KQ==";

    }
}
