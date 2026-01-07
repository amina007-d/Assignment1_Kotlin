data class Contact(
    val id: Int,
    val name: String,
    val phone: String,
    val email: String?
)

fun main() {

    val contactsById = mutableMapOf<Int, Contact>()
    val contactsByPhone = mutableMapOf<String, Contact>()

    var nextId = 1

    while (true) {
        printMenu()

        when (readLine()?.trim()) {
            "1" -> nextId = addContact(contactsById, contactsByPhone, nextId)
            "2" -> showContacts(contactsById)
            "3" -> findContact(contactsById)
            "4" -> removeByPhone(contactsById, contactsByPhone)
            "5" -> {
                println("Exiting program.")
                return
            }
            else -> println("Invalid option.")
        }
    }
}

fun printMenu() {
    println(
        """
        |Contact Manager
        |1. Add contact
        |2. View all contacts
        |3. Find contact by name
        |4. Remove contact by phone number
        |5. Exit
        |Choose an option:
        """.trimMargin()
    )
}

fun addContact(
    contactsById: MutableMap<Int, Contact>,
    contactsByPhone: MutableMap<String, Contact>,
    nextId: Int
): Int {

    print("Enter name: ")
    val name = readLine()?.trim().orEmpty()

    var phone: String
    while (true) {
        print("Enter phone number (digits only): ")
        phone = readLine()?.trim().orEmpty()

        if (phone.isEmpty()) {
            println("Phone number cannot be empty.")
            continue
        }

        if (!phone.all { it.isDigit() }) {
            println("Phone number must contain digits only.")
            continue
        }

        if (contactsByPhone.containsKey(phone)) {
            println("This phone number already exists.")
            return nextId
        }

        break
    }

    print("Enter email (optional): ")
    val email = readLine()?.trim().takeIf { !it.isNullOrEmpty() }

    val contact = Contact(nextId, name, phone, email)

    contactsById[nextId] = contact
    contactsByPhone[phone] = contact

    println("Contact added with ID $nextId")
    return nextId + 1
}


fun showContacts(contactsById: Map<Int, Contact>) {
    if (contactsById.isEmpty()) {
        println("No contacts found.")
        return
    }

    contactsById.values.forEach {
        println(
            "ID: ${it.id}, Name: ${it.name}, Phone: ${it.phone}, Email: ${it.email ?: "Not provided"}"
        )
    }
}

fun findContact(contactsById: Map<Int, Contact>) {
    print("Enter name to search: ")
    val query = readLine()?.trim()?.lowercase().orEmpty()

    val results = contactsById.values.filter {
        it.name.lowercase().contains(query)
    }

    if (results.isEmpty()) {
        println("No matching contacts.")
    } else {
        results.forEach {
            println(
                "ID: ${it.id}, Name: ${it.name}, Phone: ${it.phone}, Email: ${it.email ?: "Not provided"}"
            )
        }
    }
}

fun removeByPhone(
    contactsById: MutableMap<Int, Contact>,
    contactsByPhone: MutableMap<String, Contact>
) {
    print("Enter phone number to remove: ")
    val phone = readLine()?.trim().orEmpty()

    val contact = contactsByPhone[phone]

    if (contact == null) {
        println("Contact not found.")
        return
    }

    contactsByPhone.remove(phone)
    contactsById.remove(contact.id)

    println("Contact removed.")
}
