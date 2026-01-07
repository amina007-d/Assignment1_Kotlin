import java.util.Scanner

data class Contact(
    val id: Int,
    val info: Triple<String, String, String?>
)

fun main() {
    val scanner = Scanner(System.`in`)

    val contacts = mutableListOf<Contact>()
    val contactMap = mutableMapOf<Int, Contact>()
    val phoneSet = mutableSetOf<String>()

    var nextId = 1

    while (true) {
        printMenu()

        when (scanner.nextLine().trim()) {
            "1" -> nextId = addContact(scanner, contacts, contactMap, phoneSet, nextId)
            "2" -> showContacts(contacts)
            "3" -> findContact(scanner, contacts)
            "4" -> removeByPhone(scanner, contacts, contactMap, phoneSet)
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
    contacts: MutableList<Contact>,
    contactMap: MutableMap<Int, Contact>,
    phoneSet: MutableSet<String>,
    nextId: Int
): Int {

    print("Enter name: ")
    val name = scanner.nextLine().trim()

    print("Enter phone number: ")
    val phone = scanner.nextLine().trim()

    if (!phoneSet.add(phone)) {
        println("This phone number already exists.")
        return nextId
    }

    print("Enter email (optional): ")
    val emailInput = scanner.nextLine().trim()
    val email = emailInput.ifEmpty { null }

    val contact = Contact(
        id = nextId,
        info = Triple(name, phone, email)
    )

    contacts.add(contact)
    contactMap[nextId] = contact

    println("Contact added with ID $nextId")
    return nextId + 1
}

fun showContacts(contacts: List<Contact>) {
    if (contacts.isEmpty()) {
        println("No contacts found.")
        return
    }

    contacts.forEach {
        val (name, phone, email) = it.info
        println(
            "ID: ${it.id}, Name: $name, Phone: $phone, Email: ${email ?: "Not provided"}"
        )
    }
}

fun findContact(scanner: Scanner, contacts: List<Contact>) {
    print("Enter name to search: ")
    val query = scanner.nextLine().trim().lowercase()

    val results = contacts.filter {
        it.info.first.lowercase().contains(query)
    }

    if (results.isEmpty()) {
        println("No matching contacts.")
    } else {
        results.forEach {
            val (name, phone, email) = it.info
            println(
                "ID: ${it.id}, Name: $name, Phone: $phone, Email: ${email ?: "Not provided"}"
            )
        }
    }
}

fun removeByPhone(
    scanner: Scanner,
    contacts: MutableList<Contact>,
    contactMap: MutableMap<Int, Contact>,
    phoneSet: MutableSet<String>
) {
    print("Enter phone number to remove: ")
    val phone = scanner.nextLine().trim()

    val contact = contacts.find { it.info.second == phone }

    contact?.let {
        contacts.remove(it)
        contactMap.remove(it.id)
        phoneSet.remove(phone)
        println("Contact removed.")
    } ?: println("Contact not found.")
}
