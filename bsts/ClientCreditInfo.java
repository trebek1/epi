import java.util.Map;
import java.util.HashMap;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.Set;

class ClientCreditInfo {
	int offset = 0;
	Map<String, Integer> clientToCredit = new HashMap<>();
	NavigableMap<Integer, Set<String>> creditToClients = new TreeMap<>();

	void insert(String id, int credits){
		// remove if exists
		remove(id);

		// lookup credits for certain client 
		clientToCredit.put(id, credits - offset);

		// set of clients with specific credits
		Set<String> set = creditToClients.get(credits - offset);

		if(set == null){
			set = new HashSet<>();
			creditToClients.put(c - offset, set);
		}
		set.add(id);

	}

	boolean remove(String id){
		Integer clientCredit = clientToCredit.get(id);
		if(clientCredit!= null){
			creditToClients.get(clientCredit).remove(id);
			// remove that option from the table if no entries are left after removal 
			if(creditToClients.get(clientCredit).isEmpty()){
				creditToClients.remove(clientCredit);
			}
			// remove client from table 
			clientToCredit.remove(id);
			return true;
		}
		return false;
	}

	void addAll(int C){
		offset += C;
	}

	String max(){
		return creditToClients.isEmpty() ? "" : creditToClients.lastEntry().getValue().iterator().next();
	}
}







