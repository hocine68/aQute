package aQute.service.github;

/*
 * <pre>
 * {
 *   "name": "master",
 *   "commit": {
 *     "sha": "6dcb09b5b57875f334f61aebed695e2e4193db5e",
 *     "url": "https://api.github.com/repos/octocat/Hello-World/commits/c5b97d5ae6c19d5c5df71a34c7fbeeda2479ccbc"
 *   }
 * }</pre>
 */
public class Branch implements Comparable<Branch> {
	public String		name;
	public Reference	commit;

	public int compareTo(Branch o) {
		return name.compareTo(o.name);
	}
}