package de.exb.interviews.shalabi.api.service;



import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

public interface FileService {
	@NotNull
	public OutputStream openForWriting( @NotNull final File ioFile,
                                       final boolean aAppend)
		throws FileServiceException;

	@NotNull
	public InputStream openForReading( @NotNull final File ioFile)
			throws FileServiceException;

	@NotNull
	public URL construct( @NotNull final String aPath) throws FileServiceException;

	@NotNull
	public URL construct( @NotNull final URL aParentPath, @NotNull final String aChildPath)
		throws FileServiceException;

	public boolean createNewFile( @NotNull final File ioFile) throws FileServiceException;

	public boolean mkdir( @NotNull final File ioFile);

	public boolean mkdirs( @NotNull final File ioFile);

	@NotNull
	public List<de.exb.interviews.shalabi.api.storage.File> list( @NotNull final File ioFile) throws FileServiceException;

	public void delete( @NotNull final File ioFile, final boolean aRecursive)
			throws FileServiceException;

	public boolean exists( @NotNull final File ioFile) throws FileServiceException;

	public boolean isFile( @NotNull final File ioFile) throws FileServiceException;

	public boolean isDirectory( @NotNull final File ioFile) throws FileServiceException;

	public long getSize( @NotNull final File ioFile) throws FileServiceException;

	@NotNull
	public URL getParent( @NotNull final File ioFile) throws FileServiceException;

	@NotNull
	public String getName( @NotNull final File ioFile) throws FileServiceException;
}
