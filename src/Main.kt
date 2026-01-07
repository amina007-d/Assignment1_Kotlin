import java.util.Scanner

data class Contact(
    val id: Int,
    val name: String,
    val phone: String,
    val email: String?
)

fun main() {
    val scanner = Scanner(System.`in`)

    val contactsById = mutableMapOf<Int, Contact>()
    val contactsByPhone = mutableMapOf<String, Contact>()

    var nextId = 1

    while (true) {
        printMenu()

        when (scanner.nextLine().trim()) {
            "1" -> nextId = addContact(scanner, contactsById, contactsByPhone, nextId)
            "2" -> showContacts(contactsById)
            "3" -> findContact(scanner, contactsById)
            "4" -> removeByPhone(scanner, contactsById, contactsByPhone)
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
    scanner: Scanner,
    contactsById: MutableMap<Int, Contact>,
    contactsByPhone: MutableMap<String, Contact>,
    nextId: Int
): Int {

    print("Enter name: ")
    val name = scanner.nextLine().trim()

    print("Enter phone number: ")
    val phone = scanner.nextLine().trim()

    if (contactsByPhone.containsKey(phone)) {
        println("This phone number already exists.")
        return nextId
    }

    print("Enter email (optional): ")
    val email = scanner.nextLine().trim().ifEmpty { null }

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

fun findContact(scanner: Scanner, contactsById: Map<Int, Contact>) {
    print("Enter name to search: ")
    val query = scanner.nextLine().trim().lowercase()

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
    scanner: Scanner,
    contactsById: MutableMap<Int, Contact>,
    contactsByPhone: MutableMap<String, Contact>
) {
    print("Enter phone number to remove: ")
    val phone = scanner.nextLine().trim()

    val contact = contactsByPhone[phone]

    if (contact == null) {
        println("Contact not found.")
        return
    }

    contactsByPhone.remove(phone)
    contactsById.remove(contact.id)

    println("Contact removed.")
}
