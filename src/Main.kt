data class Contact(
    val id: Int,
    val name: String,
    val phone: String,
    val email: String?
)

fun main() {

    val contactsById = mutableMapOf<Int, Contact>()

    val contactsByPhone = mutableMapOf<String, Contact>()

    val contactList = mutableListOf<Contact>()

    val uniqueNames = mutableSetOf<String>()

    var nextId = 1

    while (true) {
        printMenu()

        when (readln().trim()) {
            "1" -> {
                val result: Pair<Contact?, Int> =
                    addContact(contactsById, contactsByPhone, contactList, uniqueNames, nextId)

                result.first?.let {
                    println("Contact added with ID ${it.id}")
                }

                nextId = result.second
            }

            "2" -> showContacts(contactList)

            "3" -> findContact(contactList)

            "4" -> removeByPhone(contactsById, contactsByPhone, contactList)

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
    contactList: MutableList<Contact>,
    uniqueNames: MutableSet<String>,
    nextId: Int
): Pair<Contact?, Int> {

    print("Enter name: ")
    val name = readln().trim()
    if (name.isEmpty()) {
        println("Name cannot be empty.")
        return Pair(null, nextId)
    }

    print("Enter phone number (digits only): ")
    val phone = readln().trim()

    if (phone.isEmpty() || !phone.all { it.isDigit() }) {
        println("Invalid phone number.")
        return Pair(null, nextId)
    }

    if (contactsByPhone.containsKey(phone)) {
        println("This phone number already exists.")
        return Pair(null, nextId)
    }

    print("Enter email (optional): ")
    val email = readln().trim().takeIf { it.isNotEmpty() }

    val contact = Contact(nextId, name, phone, email)

    contactsById[nextId] = contact
    contactsByPhone[phone] = contact
    contactList.add(contact)
    uniqueNames.add(name.lowercase())

    return Pair(contact, nextId + 1)
}

fun showContacts(contactList: List<Contact>) {
    if (contactList.isEmpty()) {
        println("No contacts found.")
        return
    }

    contactList.forEach {
        println(
            "ID: ${it.id}, Name: ${it.name}, Phone: ${it.phone}, Email: ${it.email ?: "Not provided"}"
        )
    }
}

fun findContact(contactList: List<Contact>) {
    print("Enter name to search: ")
    val query = readln().trim().lowercase()

    val results = contactList.filter {
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
    contactsByPhone: MutableMap<String, Contact>,
    contactList: MutableList<Contact>
) {
    print("Enter phone number to remove: ")
    val phone = readln().trim()

    val contact = contactsByPhone[phone]
    contact?.let {
        contactsByPhone.remove(phone)
        contactsById.remove(it.id)
        contactList.remove(it)
        println("Contact removed.")
    } ?: println("Contact not found.")
}
