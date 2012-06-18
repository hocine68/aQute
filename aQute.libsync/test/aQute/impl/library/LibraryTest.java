package aQute.impl.library;

import junit.framework.*;
import aQute.bnd.annotation.component.*;
import aQute.impl.store.mongo.*;
import aQute.service.library.Library.Program;
import aQute.service.library.Library.Revision;
import aQute.service.library.Library.RevisionRef;
import aQute.test.dummy.ds.*;
import aQute.test.dummy.log.*;

public class LibraryTest extends TestCase {

	MongoDBImpl	mongo;
	LibraryImpl	lib;

	@Reference
	void setMongo(MongoDBImpl mongo) throws Exception {
		this.mongo = mongo;
	}

	@Reference
	void setLibrary(LibraryImpl lib) throws Exception {
		this.lib = lib;
	}

	public void setUp() throws Exception {
		DummyDS ds = new DummyDS();
		ds.add(this);
		ds.add(MongoDBImpl.class).$("db", "test");
		ds.add(new DummyLog().direct().stacktrace());
		ds.add(new LibraryImpl());
		ds.wire();
		mongo.getStore(Revision.class, "library").all().remove();
	}
	
	public void testSimple() throws Exception {		
		assertNotNull(lib);
		Revision revision = lib.insert( "file:/Ws/posthooktest/aQute.libg/aQute.libg-2.7.0.jar");
		assertNotNull("Should have created a rvision", revision);
		assertEquals( revision.bsn, "aQute.libg");
		assertEquals( revision.version, "2.7.0");
		Revision revision2 = lib.insert( "file:/Ws/posthooktest/aQute.libg/aQute.libg-2.7.1.jar");
		assertNotNull(revision2);
		
		Program program = lib.getProgram("aQute.libg");
		assertNotNull(program);
		assertEquals( 2, program.revisions.size() );
		RevisionRef x270 = program.revisions.get(0);
		RevisionRef x271 = program.revisions.get(1);
		
		assertNotNull(x270);
		assertEquals("aQute.libg", x270.bsn);
		assertEquals("2.7.0", x270.version);
		assertNotNull(x271);
		assertEquals("aQute.libg", x271.bsn);
		assertEquals("2.7.1", x271.version);
	}
}
