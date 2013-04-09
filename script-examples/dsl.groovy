class Bank {
    def actions = []

    def transaction(closure) {
        closure.delegate = this
        closure()
        actions.each { it.execute() }
    }

    def getProperty(String name) {
        name
    }

    def methodMissing(String name, args) {
        def className = name[0].toUpperCase() + name[1..-1]
        actions << this.getClass().getClassLoader().loadClass("${className}Action").newInstance(amount: args.first())
        actions.last()
    }
}

class DepositAction {
    def amount
    def toAccount

    def to(account) {
        this.toAccount = account
    }

    def execute() {
        println "depositing $amount to $toAccount"
    }
}

class TransferAction {
    def amount
    def toAccount
    def fromAccount

    def methodMissing(String name, args) {
        this."${name}Account" = args.first()
        this
    }

    def execute() {
        println "transfering $amount from $fromAccount to $toAccount"
    }
}

def bank = new Bank()

bank.transaction {
    deposit '$20.00' to checking
    transfer '$50.00' from checking to savings
}
